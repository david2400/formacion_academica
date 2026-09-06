package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.http;

import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import com.kleverkids.formacion_academica.modules.estados.domain.exception.MotorEstadosException;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoMotor;
import com.kleverkids.formacion_academica.modules.estados.domain.model.MaquinaEstados;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.http.dto.MotorEstadosRespuestas.AccionDisponible;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.http.dto.MotorEstadosRespuestas.Maquina;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.http.dto.MotorEstadosRespuestas.ResultadoTransicion;
import com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.http.dto.MotorEstadosRespuestas.VistaEstado;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Cliente HTTP del motor de estados de access_control.
 *
 * <p><b>Acoplamiento asumido.</b> Estas llamadas ocurren dentro de transacciones de
 * escritura: si el motor no responde, la operación de negocio falla. Es el precio
 * de que el estado tenga una única fuente de verdad. La alternativa —seguir
 * adelante y registrar el cambio más tarde— dejaría las dos bases divergiendo en
 * silencio, que es peor que una caída visible.
 *
 * <p>La lectura de definiciones sí se cachea, porque una máquina publicada es
 * inmutable. Eso deja fuera del camino remoto el caso más frecuente con diferencia:
 * consultar el estado inicial al crear una entidad.
 */
@Component
public class MotorEstadosHttpAdapter implements MotorEstadosPort {

    private static final Logger log = LoggerFactory.getLogger(MotorEstadosHttpAdapter.class);

    private final RestClient http;
    private final MotorEstadosProperties propiedades;
    private final Map<String, EntradaCache> cache = new ConcurrentHashMap<>();

    public MotorEstadosHttpAdapter(RestClient motorEstadosRestClient,
                                   MotorEstadosProperties propiedades) {
        this.http = motorEstadosRestClient;
        this.propiedades = propiedades;
    }

    // ── Definición ──────────────────────────────────────────────────────────

    @Override
    public Long estadoInicial(String maquina) {
        return definicion(maquina).estadoInicial();
    }

    @Override
    public boolean perteneceALaMaquina(String maquina, Long estadoId) {
        return definicion(maquina).contiene(estadoId);
    }

    /**
     * La definición vigente, de caché si sigue fresca.
     *
     * <p>No se sincroniza el acceso: dos hilos pueden pedir la misma máquina a la
     * vez y hacer dos peticiones. Es inofensivo —la respuesta es idéntica y el
     * último gana— y evita un bloqueo en el camino de escritura.
     */
    private MaquinaEstados definicion(String maquina) {
        EntradaCache entrada = cache.get(maquina);
        if (entrada != null && !entrada.caducada(propiedades.getCacheTtl())) {
            return entrada.maquina();
        }

        MaquinaEstados fresca = descargarDefinicion(maquina);
        cache.put(maquina, new EntradaCache(fresca, Instant.now()));
        return fresca;
    }

    private MaquinaEstados descargarDefinicion(String maquina) {
        Maquina respuesta = ejecutar(
                () -> http.get()
                        .uri("/state-machines/by-code/{code}", maquina)
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, (peticion, error) -> {
                            throw traducir(error.getStatusCode(), leerCuerpo(error),
                                    "consultando la máquina '" + maquina + "'");
                        })
                        .body(Maquina.class),
                "consultando la máquina '" + maquina + "'");

        if (respuesta == null || respuesta.states() == null || respuesta.states().isEmpty()) {
            throw MotorEstadosException.configuracion(
                    "La máquina '" + maquina + "' no existe publicada en el motor o no tiene estados");
        }

        List<EstadoMotor> estados = respuesta.states().stream()
                .map(e -> new EstadoMotor(
                        e.id(),
                        e.code(),
                        e.name(),
                        Boolean.TRUE.equals(e.isInitial()),
                        Boolean.TRUE.equals(e.isFinal())))
                .toList();

        log.debug("Definición de '{}' descargada: v{}, {} estados",
                maquina, respuesta.version(), estados.size());

        return new MaquinaEstados(respuesta.id(), respuesta.code(), respuesta.version(), estados);
    }

    // ── Ejecución ───────────────────────────────────────────────────────────

    @Override
    public void iniciarCiclo(String maquina, String tipoEntidad, Long entidadId) {
        try {
            ejecutar(
                    () -> http.post()
                            .uri("/entities/{tipo}/{id}/state-machine/initialize", tipoEntidad, entidadId)
                            .contentType(MediaType.APPLICATION_JSON)
                            .body(Map.of("state_machine", maquina))
                            .retrieve()
                            .onStatus(HttpStatusCode::isError, (peticion, error) -> {
                                throw traducir(error.getStatusCode(), leerCuerpo(error),
                                        "arrancando el ciclo de " + tipoEntidad + " " + entidadId);
                            })
                            .body(String.class),
                    "arrancando el ciclo de " + tipoEntidad + " " + entidadId);
        } catch (MotorEstadosException e) {
            // Que la instancia ya exista no es un fallo: significa que el ciclo
            // arrancó antes. Tratarlo como error obligaría a cada llamador a
            // distinguir "primera vez" de "reintento", que es justo lo que esta
            // operación promete resolver.
            if (e.getCausa() == MotorEstadosException.Causa.RECHAZADA
                    && e.getMessage() != null
                    && e.getMessage().contains("INSTANCE_ALREADY_EXISTS")) {
                log.debug("El ciclo de {} {} ya estaba arrancado", tipoEntidad, entidadId);
                return;
            }
            throw e;
        }
    }

    @Override
    public Long moverAEstado(String maquina, String tipoEntidad, Long entidadId,
                             Long estadoDestino, String motivo) {

        MaquinaEstados definicion = definicion(maquina);

        // RECHAZADA y no CONFIGURACION: que el cliente mande un id de otra máquina es
        // un dato malo suyo, no algo roto en el motor. Clasificarlo como configuración
        // lo devolvía como 500 y lo mezclaba con las averías de verdad.
        EstadoMotor destino = definicion.porId(estadoDestino)
                .orElseThrow(() -> MotorEstadosException.rechazada(
                        "El estado " + estadoDestino + " no pertenece a la máquina '" + maquina
                                + "'. Estados válidos: " + definicion.codigosDisponibles()));

        VistaEstado vista = consultarVistaArrancandoSiHaceFalta(maquina, tipoEntidad, entidadId);

        if (vista.currentState() != null && estadoDestino.equals(vista.currentState().id())) {
            return estadoDestino;
        }

        List<AccionDisponible> candidatas = (vista.availableActions() == null ? List.<AccionDisponible>of()
                : vista.availableActions()).stream()
                .filter(a -> destino.codigo().equals(a.targetState()))
                .toList();

        if (candidatas.isEmpty()) {
            String alcanzables = vista.availableActions() == null ? "(ninguno)"
                    : vista.availableActions().stream()
                            .map(AccionDisponible::targetState)
                            .distinct()
                            .reduce((a, b) -> a + ", " + b)
                            .orElse("(ninguno)");
            throw MotorEstadosException.rechazada(
                    "No se puede pasar a '" + destino.codigo() + "' desde el estado actual de "
                            + tipoEntidad + " " + entidadId + ". Destinos alcanzables ahora: " + alcanzables);
        }

        if (candidatas.size() > 1) {
            // Determinismo garantiza una transición por (estado, acción), no una
            // acción por destino. Elegir al azar registraría una acción falsa en el
            // historial, así que es preferible parar y que se corrija el grafo.
            throw MotorEstadosException.configuracion(
                    "Hay " + candidatas.size() + " acciones que llevan a '" + destino.codigo()
                            + "' en la máquina '" + maquina + "'. El destino no identifica la acción; "
                            + "esta operación necesita que sea única.");
        }

        AccionDisponible accion = candidatas.get(0);

        if (Boolean.TRUE.equals(accion.requiresReason()) && (motivo == null || motivo.isBlank())) {
            throw MotorEstadosException.rechazada(
                    "La acción '" + accion.code() + "' exige un motivo y no se recibió ninguno");
        }

        ResultadoTransicion resultado = ejecutar(
                () -> http.post()
                        .uri(uri -> uri.path("/entities/{tipo}/{id}/state-machine/transition")
                                .queryParam("state_machine", maquina)
                                .build(tipoEntidad, entidadId))
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(motivo == null || motivo.isBlank()
                                ? Map.of("action", accion.code())
                                : Map.of("action", accion.code(), "reason", motivo))
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, (peticion, error) -> {
                            throw traducir(error.getStatusCode(), leerCuerpo(error),
                                    "ejecutando '" + accion.code() + "' sobre " + tipoEntidad + " " + entidadId);
                        })
                        .body(ResultadoTransicion.class),
                "ejecutando '" + accion.code() + "' sobre " + tipoEntidad + " " + entidadId);

        if (resultado == null || resultado.currentState() == null) {
            throw MotorEstadosException.configuracion(
                    "El motor aceptó la transición pero no devolvió el estado resultante");
        }

        return resultado.currentState().id();
    }

    /**
     * Consulta el estado y, si la entidad todavía no tiene instancia, la arranca.
     *
     * <p>Las filas creadas antes de esta integración nunca pasaron por el motor.
     * Sin esto quedarían bloqueadas para siempre: no se les podría cambiar el estado
     * porque el motor no las conoce, y nada las daría de alta. Se arrancan en el
     * estado inicial, que es lo único honesto: de su pasado no hay historia que
     * reconstruir.
     */
    private VistaEstado consultarVistaArrancandoSiHaceFalta(String maquina, String tipoEntidad,
                                                            Long entidadId) {
        try {
            return consultarVista(maquina, tipoEntidad, entidadId);
        } catch (MotorEstadosException e) {
            if (!esInstanciaInexistente(e)) {
                throw e;
            }
            log.info("La entidad {} {} no tenía ciclo en '{}'; se arranca ahora",
                    tipoEntidad, entidadId, maquina);
            iniciarCiclo(maquina, tipoEntidad, entidadId);
            return consultarVista(maquina, tipoEntidad, entidadId);
        }
    }

    /**
     * El motor responde 409 con {@code CURRENT_STATE_NOT_FOUND} cuando la entidad no
     * tiene instancia. Se compara contra el cuerpo porque es el código estable del
     * contrato; el texto del mensaje sí puede cambiar.
     */
    private boolean esInstanciaInexistente(MotorEstadosException e) {
        return e.getCausa() == MotorEstadosException.Causa.RECHAZADA
                && e.getMessage() != null
                && e.getMessage().contains("CURRENT_STATE_NOT_FOUND");
    }

    private VistaEstado consultarVista(String maquina, String tipoEntidad, Long entidadId) {
        VistaEstado vista = ejecutar(
                () -> http.get()
                        .uri(uri -> uri.path("/entities/{tipo}/{id}/state-machine")
                                .queryParam("state_machine", maquina)
                                .build(tipoEntidad, entidadId))
                        .accept(MediaType.APPLICATION_JSON)
                        .retrieve()
                        .onStatus(HttpStatusCode::isError, (peticion, error) -> {
                            throw traducir(error.getStatusCode(), leerCuerpo(error),
                                    "consultando el estado de " + tipoEntidad + " " + entidadId);
                        })
                        .body(VistaEstado.class),
                "consultando el estado de " + tipoEntidad + " " + entidadId);

        if (vista == null) {
            throw MotorEstadosException.configuracion(
                    "El motor no devolvió estado para " + tipoEntidad + " " + entidadId
                            + " en la máquina '" + maquina + "'");
        }
        return vista;
    }

    // ── Plumbing ────────────────────────────────────────────────────────────

    /**
     * Envuelve una llamada para que un fallo de red no salga como excepción de
     * Spring. Sin esto, los módulos de negocio tendrían que conocer
     * {@code ResourceAccessException} para distinguir "el motor está caído" de
     * "el dato es inválido".
     */
    private <T> T ejecutar(java.util.function.Supplier<T> llamada, String contexto) {
        try {
            return llamada.get();
        } catch (MotorEstadosException e) {
            throw e;
        } catch (ResourceAccessException e) {
            throw MotorEstadosException.noDisponible(
                    "El motor de estados no responde (" + contexto + ")", e);
        } catch (RuntimeException e) {
            throw MotorEstadosException.noDisponible(
                    "Fallo inesperado hablando con el motor de estados (" + contexto + ")", e);
        }
    }

    private MotorEstadosException traducir(HttpStatusCode estado, String cuerpo, String contexto) {
        String detalle = cuerpo == null || cuerpo.isBlank() ? "sin detalle" : cuerpo;

        if (estado.value() == 404) {
            return MotorEstadosException.configuracion(
                    "El motor no encontró lo pedido " + contexto + ": " + detalle);
        }
        if (estado.is4xxClientError()) {
            return MotorEstadosException.rechazada(
                    "El motor rechazó la operación " + contexto + ": " + detalle);
        }
        return MotorEstadosException.noDisponible(
                "El motor devolvió " + estado.value() + " " + contexto + ": " + detalle, null);
    }

    private String leerCuerpo(org.springframework.http.client.ClientHttpResponse respuesta) {
        try {
            return new String(respuesta.getBody().readAllBytes(),
                    java.nio.charset.StandardCharsets.UTF_8);
        } catch (Exception e) {
            return null;
        }
    }

    private record EntradaCache(MaquinaEstados maquina, Instant descargadaEn) {
        boolean caducada(Duration ttl) {
            return Instant.now().isAfter(descargadaEn.plus(ttl));
        }
    }
}

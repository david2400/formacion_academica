package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.estados.application.output.contexto.EstadoContextoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoContexto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Adaptador hacia el catálogo central de estados, que vive en el servicio
 * <b>access_control</b>.
 *
 * <p>Al ser servicios y bases de datos distintas, {@code estudiantes_grupo.estado_id}
 * no puede tener llave foránea real contra el catálogo: la integridad se sostiene
 * validando contra este puerto antes de persistir.
 *
 * <p><b>Caché con degradación.</b> El catálogo cambia muy poco y se consulta en
 * cada pantalla, así que se cachea en memoria por un TTL corto. Si access_control
 * no responde y hay una copia previa —aunque esté vencida— se sirve esa: es
 * preferible un catálogo de hace unos minutos a una pantalla caída. Solo cuando no
 * hay ninguna copia se propaga el fallo como lista vacía, y entonces
 * {@code requerirEstadoInicial} falla con un mensaje explícito.
 */
@Component
public class EstadoContextoHttpAdapter implements EstadoContextoRepositoryPort {

    private static final Logger log = LoggerFactory.getLogger(EstadoContextoHttpAdapter.class);
    private static final Long EMPRESA_GLOBAL = 0L;

    private static final ParameterizedTypeReference<List<EstadoContexto>> LISTA_ESTADOS =
            new ParameterizedTypeReference<>() {
            };

    private final RestClient restClient;
    private final Duration ttl;
    private final ConcurrentHashMap<String, Entrada> cache = new ConcurrentHashMap<>();

    public EstadoContextoHttpAdapter(
            RestClient.Builder restClientBuilder,
            @Value("${access-control.base-url:http://localhost:8000}") String baseUrl,
            @Value("${access-control.catalogo-ttl-segundos:300}") long ttlSegundos) {
        this.restClient = restClientBuilder.baseUrl(baseUrl).build();
        this.ttl = Duration.ofSeconds(ttlSegundos);
    }

    @Override
    public List<EstadoContexto> listarPorContexto(String contexto, Long idEmpresa) {
        return cargar(contexto, empresa(idEmpresa));
    }

    @Override
    public Optional<EstadoContexto> obtenerInicial(String contexto, Long idEmpresa) {
        return cargar(contexto, empresa(idEmpresa)).stream()
                .filter(estado -> Boolean.TRUE.equals(estado.getEsInicial()))
                .findFirst();
    }

    @Override
    public boolean estaRegistrado(String contexto, Long estadoId, Long idEmpresa) {
        if (estadoId == null) {
            return false;
        }
        return cargar(contexto, empresa(idEmpresa)).stream()
                .anyMatch(estado -> Objects.equals(estado.getEstadoId(), estadoId));
    }

    // ── Interno ─────────────────────────────────────────────────────────────

    private Long empresa(Long idEmpresa) {
        return idEmpresa != null ? idEmpresa : EMPRESA_GLOBAL;
    }

    private List<EstadoContexto> cargar(String contexto, Long idEmpresa) {
        String clave = contexto + "|" + idEmpresa;
        Entrada actual = cache.get(clave);

        if (actual != null && !actual.vencida(ttl)) {
            return actual.estados();
        }

        try {
            List<EstadoContexto> estados = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/api/access_control/estados/contextos/{codigo}/estados")
                            .queryParam("company_id", idEmpresa)
                            .build(contexto))
                    .retrieve()
                    .body(LISTA_ESTADOS);

            List<EstadoContexto> resultado = estados != null ? estados : List.of();
            cache.put(clave, new Entrada(resultado, Instant.now()));
            return resultado;
        } catch (Exception error) {
            if (actual != null) {
                log.warn("access_control no respondió para el contexto '{}'. Se sirve la copia cacheada. Causa: {}",
                        contexto, error.getMessage());
                return actual.estados();
            }
            log.error("access_control no respondió para el contexto '{}' y no hay copia en caché. Causa: {}",
                    contexto, error.getMessage());
            return List.of();
        }
    }

    private record Entrada(List<EstadoContexto> estados, Instant momento) {
        boolean vencida(Duration ttl) {
            return Duration.between(momento, Instant.now()).compareTo(ttl) > 0;
        }
    }
}

package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.jdbc;

import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import com.kleverkids.formacion_academica.modules.estados.domain.exception.MotorEstadosException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Acceso al motor de estados por SQL directo contra el esquema {@code security}.
 *
 * <p>Ambos esquemas viven en el mismo servidor MySQL, así que basta con prefijar el
 * nombre. Esto tiene dos consecuencias buenas y una mala, y conviene tenerlas
 * presentes las tres.
 *
 * <p><b>Bueno 1: no depende de que access_control esté arriba.</b> Solo de MySQL,
 * que este servicio ya necesita.
 *
 * <p><b>Bueno 2: una sola transacción.</b> Los cambios en {@code security} y en
 * {@code academia} viajan por la misma conexión, así que si falla el guardado local
 * el cambio de estado se deshace con él. Por HTTP eso era imposible y quedaba como
 * deuda conocida.
 *
 * <p><b>Malo: esto reimplementa parte del orquestador del motor.</b> Las mismas
 * reglas escritas en dos proyectos tienden a divergir. La defensa aquí es que esta
 * implementación <b>se niega a operar fuera del subconjunto que sabe validar</b>:
 * si una transición tiene reglas configuradas o exige permiso, falla en vez de
 * seguir adelante. Dar por buena una validación que no se ejecutó sería peor que
 * rechazar la operación.
 *
 * <p>La lectura de la definición va contra las <b>vistas</b> {@code vw_sm_*}, que
 * son el contrato publicado. Las escrituras van contra las tablas, porque no hay
 * alternativa. Ojo: las tablas de configuración usan borrado lógico y Hibernate lo
 * aplica solo a sus propias consultas; aquí, en SQL nativo, el filtro
 * {@code deleted = 0} lo ponen las vistas.
 */
@Component
public class MotorEstadosJdbcAdapter implements MotorEstadosPort {

    private static final Logger log = LoggerFactory.getLogger(MotorEstadosJdbcAdapter.class);

    private final JdbcTemplate jdbc;
    private final String esquema;

    public MotorEstadosJdbcAdapter(JdbcTemplate jdbc, MotorEstadosProperties propiedades) {
        this.jdbc = jdbc;
        // Validado como identificador simple en el setter de las propiedades: nunca
        // llega de una petición, solo de la configuración del despliegue.
        this.esquema = propiedades.getEsquema();
    }

    // ── Definición ──────────────────────────────────────────────────────────

    /**
     * No se cachea. Con el motor detrás de HTTP la caché evitaba una llamada de red;
     * aquí es una consulta local sobre una vista indexada, y cachearla solo añadiría
     * una ventana en la que este servicio no ve una versión recién publicada.
     */
    @Override
    public Long estadoInicial(String maquina) {
        List<Long> ids = jdbc.query(
                "SELECT estado_id FROM " + esquema + ".vw_sm_estado "
                        + "WHERE maquina = ? AND maquina_status = 'PUBLISHED' AND es_inicial = 1",
                (rs, n) -> rs.getLong("estado_id"),
                maquina);

        if (ids.isEmpty()) {
            throw MotorEstadosException.configuracion(
                    "La máquina '" + maquina + "' no existe publicada en el motor o no tiene estado inicial");
        }
        if (ids.size() > 1) {
            // El motor valida esta regla al publicar. Incumplirla significa que alguien
            // tocó la base por debajo; asignar uno al azar dejaría entidades arrancando
            // en estados distintos sin que nadie se entere.
            throw MotorEstadosException.configuracion(
                    "La máquina '" + maquina + "' tiene " + ids.size()
                            + " estados iniciales y debería tener exactamente uno");
        }
        return ids.get(0);
    }

    // ── Ejecución ───────────────────────────────────────────────────────────

    /**
     * Alta de la instancia y primer asiento del historial.
     *
     * <p>La inserción lleva su propia guarda {@code NOT EXISTS} en la misma
     * sentencia, así que es idempotente sin necesidad de consultar antes. El
     * historial solo se escribe si la instancia se creó de verdad: si no, cada
     * reintento añadiría un arranque falso.
     */
    @Override
    public void iniciarCiclo(String maquina, String tipoEntidad, Long entidadId) {
        int creadas = jdbc.update(
                "INSERT INTO " + esquema + ".state_machine_instance "
                        + "  (state_machine_id, entity_type_id, entity_id, current_state_id, "
                        + "   version, started_at, usr_crea, created_at) "
                        + "SELECT v.maquina_id, v.tipo_entidad_id, ?, v.estado_id, 0, NOW(6), 0, NOW(6) "
                        + "FROM " + esquema + ".vw_sm_estado v "
                        + "WHERE v.maquina = ? AND v.tipo_entidad = ? "
                        + "  AND v.maquina_status = 'PUBLISHED' AND v.es_inicial = 1 "
                        + "  AND NOT EXISTS ( "
                        + "      SELECT 1 FROM " + esquema + ".state_machine_instance i "
                        + "      WHERE i.state_machine_id = v.maquina_id "
                        + "        AND i.entity_type_id = v.tipo_entidad_id "
                        + "        AND i.entity_id = ?)",
                entidadId, maquina, tipoEntidad, entidadId);

        if (creadas == 0) {
            // O ya existía —caso normal al reasignar— o la máquina no case con el tipo
            // de entidad. Se distinguen preguntando, porque callar la segunda dejaría
            // entidades sin ciclo de vida y sin ningún aviso.
            if (!existeInstancia(maquina, tipoEntidad, entidadId)) {
                throw MotorEstadosException.configuracion(
                        "No se pudo arrancar el ciclo de " + tipoEntidad + " " + entidadId
                                + ": la máquina '" + maquina + "' no está publicada para ese tipo de entidad"
                                + " o no tiene estado inicial");
            }
            log.debug("El ciclo de {} {} ya estaba arrancado", tipoEntidad, entidadId);
            return;
        }

        // El arranque también es historia: sin él, el primer tramo del ciclo de vida
        // quedaría sin registrar.
        jdbc.update(
                "INSERT INTO " + esquema + ".state_transition_history "
                        + "  (instance_id, from_state_id, to_state_id, action_id, user_id, created_at) "
                        + "SELECT i.id_instance, NULL, i.current_state_id, NULL, NULL, NOW(6) "
                        + "FROM " + esquema + ".state_machine_instance i "
                        + "JOIN " + esquema + ".vw_sm_estado v "
                        + "       ON v.maquina_id = i.state_machine_id AND v.estado_id = i.current_state_id "
                        + "WHERE v.maquina = ? AND v.tipo_entidad = ? AND i.entity_id = ?",
                maquina, tipoEntidad, entidadId);

        log.info("Ciclo arrancado: {} {} en '{}'", tipoEntidad, entidadId, maquina);
    }

    private boolean existeInstancia(String maquina, String tipoEntidad, Long entidadId) {
        Integer n = jdbc.queryForObject(
                "SELECT COUNT(*) FROM " + esquema + ".state_machine_instance i "
                        + "JOIN " + esquema + ".vw_sm_estado v "
                        + "       ON v.maquina_id = i.state_machine_id AND v.estado_id = i.current_state_id "
                        + "WHERE v.maquina = ? AND v.tipo_entidad = ? AND i.entity_id = ?",
                Integer.class, maquina, tipoEntidad, entidadId);
        return n != null && n > 0;
    }

    /**
     * Reproduce los pasos del orquestador del motor, en el mismo orden y con las
     * mismas condiciones de fallo. Ver {@code StateTransitionService.ejecutar} en
     * access_control: cualquier cambio allí hay que replicarlo aquí.
     */
    @Override
    public Long moverAEstado(String maquina, String tipoEntidad, Long entidadId,
                             Long estadoDestino, String motivo) {

        // 1. Destino válido dentro de la máquina. Es un dato del cliente, así que el
        //    fallo es suyo, no una avería del motor.
        Estado destino = buscarEstado(maquina, estadoDestino)
                .orElseThrow(() -> MotorEstadosException.rechazada(
                        "El estado " + estadoDestino + " no pertenece a la máquina '" + maquina
                                + "'. Estados válidos: " + codigosDe(maquina)));

        // 2. Instancia. Las filas creadas antes de esta integración no tienen ninguna;
        //    se arrancan al vuelo en el estado inicial, que es lo único honesto: de su
        //    pasado no hay historia que reconstruir.
        Instancia instancia = buscarInstancia(maquina, tipoEntidad, entidadId).orElse(null);
        if (instancia == null) {
            log.info("La entidad {} {} no tenía ciclo en '{}'; se arranca ahora",
                    tipoEntidad, entidadId, maquina);
            iniciarCiclo(maquina, tipoEntidad, entidadId);
            instancia = buscarInstancia(maquina, tipoEntidad, entidadId)
                    .orElseThrow(() -> MotorEstadosException.configuracion(
                            "No se pudo arrancar el ciclo de " + tipoEntidad + " " + entidadId));
        }

        // 3. Ya está donde se pide. Repetir la petición no debe generar un asiento
        //    nuevo en el historial.
        if (estadoDestino.equals(instancia.estadoId())) {
            return estadoDestino;
        }

        // 4. Un estado terminal no admite salidas.
        if (instancia.esFinal()) {
            throw MotorEstadosException.rechazada(
                    "La entidad " + tipoEntidad + " " + entidadId + " está en '" + instancia.estado()
                            + "', que es un estado final y no admite más cambios");
        }

        // 5. Qué acción lleva del estado actual al destino.
        List<Transicion> salidas = transicionesDesde(maquina, instancia.estadoId());
        List<Transicion> candidatas = salidas.stream()
                .filter(t -> estadoDestino.equals(t.hastaId()))
                .toList();

        if (candidatas.isEmpty()) {
            String alcanzables = salidas.isEmpty() ? "(ninguno)"
                    : salidas.stream().map(Transicion::hasta).distinct().reduce((a, b) -> a + ", " + b).orElse("(ninguno)");
            throw MotorEstadosException.rechazada(
                    "No se puede pasar de '" + instancia.estado() + "' a '" + destino.codigo() + "' en "
                            + tipoEntidad + " " + entidadId + ". Destinos alcanzables ahora: " + alcanzables);
        }
        if (candidatas.size() > 1) {
            // Determinismo garantiza una transición por (estado, acción), no una acción
            // por destino. Elegir al azar registraría una acción falsa en el historial.
            throw MotorEstadosException.configuracion(
                    "Hay " + candidatas.size() + " acciones que llevan de '" + instancia.estado()
                            + "' a '" + destino.codigo() + "' en '" + maquina + "'. El destino no identifica"
                            + " la acción; esta operación necesita que sea única.");
        }

        Transicion transicion = candidatas.get(0);

        // 6. Los límites de esta implementación, explícitos.
        rechazarSiNoSePuedeValidar(transicion, maquina);

        // 7. Motivo obligatorio.
        if (transicion.exigeMotivo() && (motivo == null || motivo.isBlank())) {
            throw MotorEstadosException.rechazada(
                    "La acción '" + transicion.accion() + "' exige un motivo y no se recibió ninguno");
        }

        // 8. Cambio con bloqueo optimista, con la misma semántica de completed_at que
        //    el motor: se sella al llegar por primera vez a un final y se limpia si la
        //    entidad sale de él.
        int filas = jdbc.update(
                "UPDATE " + esquema + ".state_machine_instance "
                        + "SET current_state_id = ?, "
                        + "    version = version + 1, "
                        + "    updated_at = NOW(6), "
                        + "    completed_at = CASE WHEN ? = 1 THEN COALESCE(completed_at, NOW(6)) ELSE NULL END "
                        + "WHERE id_instance = ? AND version = ?",
                estadoDestino, destino.esFinal() ? 1 : 0, instancia.id(), instancia.version());

        if (filas == 0) {
            throw MotorEstadosException.concurrente(
                    "Otra operación cambió el estado de " + tipoEntidad + " " + entidadId
                            + " mientras se procesaba esta. Vuelve a consultarlo y reintenta.");
        }

        // 9. Historial.
        jdbc.update(
                "INSERT INTO " + esquema + ".state_transition_history "
                        + "  (instance_id, from_state_id, to_state_id, action_id, user_id, reason, created_at) "
                        + "VALUES (?, ?, ?, ?, NULL, ?, NOW(6))",
                instancia.id(), instancia.estadoId(), estadoDestino, transicion.accionId(), motivo);

        log.info("Transición {} {}: {} -> {} por acción {}",
                tipoEntidad, entidadId, instancia.estado(), destino.codigo(), transicion.accion());

        return estadoDestino;
    }

    /**
     * Frena la operación cuando la transición exige algo que aquí no se puede
     * comprobar.
     *
     * <p>Este adaptador no evalúa reglas —viven como {@code @Component} en
     * access_control— ni comprueba permisos —este servicio no tiene contexto de
     * seguridad ni usuario que propagar—. Mientras nadie configure ninguna de las dos
     * cosas, el subconjunto implementado es equivalente al del motor. En cuanto
     * alguien las configure, esto avisa en vez de saltárselas.
     */
    private void rechazarSiNoSePuedeValidar(Transicion transicion, String maquina) {
        if (transicion.reglas() > 0) {
            throw MotorEstadosException.configuracion(
                    "La transición '" + transicion.nombre() + "' de '" + maquina + "' tiene "
                            + transicion.reglas() + " regla(s) configurada(s), y este servicio no sabe"
                            + " evaluarlas: viven en access_control. Ejecuta la transición por su API"
                            + " o quita la regla.");
        }
        if (transicion.permiso() != null) {
            throw MotorEstadosException.configuracion(
                    "La transición '" + transicion.nombre() + "' de '" + maquina + "' exige el permiso '"
                            + transicion.permiso() + "', y este servicio no puede comprobarlo: no tiene"
                            + " usuario autenticado que propagar. Ejecuta la transición por el API de"
                            + " access_control o quita la exigencia.");
        }
    }

    // ── Consultas ───────────────────────────────────────────────────────────

    private Optional<Estado> buscarEstado(String maquina, Long estadoId) {
        return jdbc.query(
                "SELECT estado_id, estado, es_final FROM " + esquema + ".vw_sm_estado "
                        + "WHERE maquina = ? AND maquina_status = 'PUBLISHED' AND estado_id = ?",
                (rs, n) -> new Estado(rs.getLong("estado_id"), rs.getString("estado"), rs.getBoolean("es_final")),
                maquina, estadoId).stream().findFirst();
    }

    private String codigosDe(String maquina) {
        List<String> codigos = jdbc.query(
                "SELECT estado FROM " + esquema + ".vw_sm_estado "
                        + "WHERE maquina = ? AND maquina_status = 'PUBLISHED' ORDER BY orden",
                (rs, n) -> rs.getString("estado"), maquina);
        return codigos.isEmpty() ? "(ninguno)" : String.join(", ", codigos);
    }

    private Optional<Instancia> buscarInstancia(String maquina, String tipoEntidad, Long entidadId) {
        return jdbc.query(
                "SELECT i.id_instance, i.current_state_id, i.version, v.estado, v.es_final "
                        + "FROM " + esquema + ".state_machine_instance i "
                        + "JOIN " + esquema + ".vw_sm_estado v "
                        + "       ON v.maquina_id = i.state_machine_id AND v.estado_id = i.current_state_id "
                        + "WHERE v.maquina = ? AND v.tipo_entidad = ? AND i.entity_id = ? "
                        + "  AND v.maquina_status = 'PUBLISHED'",
                (rs, n) -> new Instancia(
                        rs.getLong("id_instance"),
                        rs.getLong("current_state_id"),
                        rs.getLong("version"),
                        rs.getString("estado"),
                        rs.getBoolean("es_final")),
                maquina, tipoEntidad, entidadId).stream().findFirst();
    }

    private List<Transicion> transicionesDesde(String maquina, Long estadoActualId) {
        return jdbc.query(
                "SELECT transicion, accion, accion_id, hasta, hasta_id, exige_motivo, permiso, reglas "
                        + "FROM " + esquema + ".vw_sm_transicion "
                        + "WHERE maquina = ? AND maquina_status = 'PUBLISHED' AND desde_id = ? "
                        + "ORDER BY accion",
                (rs, n) -> new Transicion(
                        rs.getString("transicion"),
                        rs.getString("accion"),
                        rs.getLong("accion_id"),
                        rs.getString("hasta"),
                        rs.getLong("hasta_id"),
                        rs.getBoolean("exige_motivo"),
                        rs.getString("permiso"),
                        rs.getInt("reglas")),
                maquina, estadoActualId);
    }

    // ── Filas ───────────────────────────────────────────────────────────────

    private record Estado(Long id, String codigo, boolean esFinal) {
    }

    private record Instancia(Long id, Long estadoId, Long version, String estado, boolean esFinal) {
    }

    private record Transicion(String nombre, String accion, Long accionId, String hasta, Long hastaId,
                              boolean exigeMotivo, String permiso, int reglas) {
    }
}

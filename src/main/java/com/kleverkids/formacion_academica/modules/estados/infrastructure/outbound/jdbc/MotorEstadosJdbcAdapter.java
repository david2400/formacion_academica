package com.kleverkids.formacion_academica.modules.estados.infrastructure.outbound.jdbc;

import com.kleverkids.formacion_academica.modules.estados.application.output.MotorEstadosPort;
import com.kleverkids.formacion_academica.modules.estados.domain.exception.MotorEstadosException;
import com.kleverkids.formacion_academica.modules.estados.domain.model.TransicionMotor;
import com.kleverkids.formacion_academica.modules.estados.domain.model.EstadoMotor;
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
 * <p>Lectura y escritura van <b>directo contra las tablas</b> del motor
 * ({@code state_machine}, {@code state_machine_state}, {@code entity_type}, {@code state_transition},
 * {@code action}, {@code transition_rule}). Antes se leía de las vistas
 * {@code vw_sm_*}, pero una vista rota (definer inexistente, tabla recreada) tumbaba
 * este servicio con un error 1356 imposible de diagnosticar desde aquí, y su
 * definición ni siquiera estaba versionada. El precio: un {@code ALTER} sobre esas
 * tablas en access_control puede romper estas consultas. Si se renombra una
 * columna allí, hay que actualizar este archivo.
 *
 * <p>Ojo: las tablas de configuración usan borrado lógico y Hibernate lo aplica solo
 * a sus propias consultas. En SQL nativo no existe ese filtro, así que cada consulta
 * de este adaptador pone {@code deleted = 0} explícitamente (ver {@link #estadosVigentes}
 * y {@link #transicionesVigentes}).
 */
@Component
public class MotorEstadosJdbcAdapter implements MotorEstadosPort {

    private static final Logger log = LoggerFactory.getLogger(MotorEstadosJdbcAdapter.class);

    /** Color e icono viven en el JSON {@code state_machine_state.metadata}; no son columnas propias. */
    private static final String COLOR = "JSON_UNQUOTE(JSON_EXTRACT(s.metadata, '$.color'))";
    private static final String ICONO = "JSON_UNQUOTE(JSON_EXTRACT(s.metadata, '$.icon'))";

    private final JdbcTemplate jdbc;
    private final String esquema;

    /**
     * Estados con su máquina y tipo de entidad, ya filtrados por borrado lógico.
     * Termina en un WHERE abierto: quien lo use agrega {@code AND ...}. No filtra por
     * {@code status}: cada consulta decide si exige PUBLISHED, igual que hacían las
     * vistas a través de {@code maquina_status}.
     */
    private final String estadosVigentes;

    /** Transiciones con sus estados, acción y número de reglas activas; WHERE abierto. */
    private final String transicionesVigentes;

    public MotorEstadosJdbcAdapter(JdbcTemplate jdbc, MotorEstadosProperties propiedades) {
        this.jdbc = jdbc;
        // Validado como identificador simple en el setter de las propiedades: nunca
        // llega de una petición, solo de la configuración del despliegue.
        this.esquema = propiedades.getEsquema();

        this.estadosVigentes =
                "FROM " + esquema + ".state_machine_state s "
                        + "JOIN " + esquema + ".state_machine m ON m.id_state_machine = s.state_machine_id "
                        + "JOIN " + esquema + ".entity_type t ON t.id_entity_type = m.entity_type_id "
                        + "WHERE s.deleted = 0 AND m.deleted = 0 AND t.deleted = 0 ";

        this.transicionesVigentes =
                "FROM " + esquema + ".state_transition tr "
                        + "JOIN " + esquema + ".state_machine m ON m.id_state_machine = tr.state_machine_id "
                        + "JOIN " + esquema + ".state_machine_state sf ON sf.id_state = tr.from_state_id "
                        + "JOIN " + esquema + ".state_machine_state s ON s.id_state = tr.to_state_id "
                        + "JOIN " + esquema + ".`action` a ON a.id_action = tr.action_id "
                        + "WHERE tr.deleted = 0 AND tr.active = 1 AND m.deleted = 0 "
                        + "  AND sf.deleted = 0 AND s.deleted = 0 AND a.deleted = 0 ";
    }

    /** Columnas de una transición; {@code s} es el estado destino. */
    private String columnasTransicion() {
        return "SELECT tr.name AS transicion, a.code AS accion, a.id_action AS accion_id, "
                + "       a.name AS accion_nombre, sf.id_state AS desde_id, sf.code AS desde, "
                + "       s.id_state AS hasta_id, s.code AS hasta, s.name AS hasta_nombre, "
                + "       " + COLOR + " AS hasta_color, s.is_final AS hasta_es_final, "
                + "       tr.requires_reason AS exige_motivo, tr.permission_code AS permiso, "
                + "       (SELECT COUNT(*) FROM " + esquema + ".transition_rule r "
                + "         WHERE r.state_transition_id = tr.id_state_transition "
                + "           AND r.deleted = 0 AND r.active = 1) AS reglas ";
    }

    // ── Definición ──────────────────────────────────────────────────────────

    /**
     * No se cachea. Con el motor detrás de HTTP la caché evitaba una llamada de red;
     * aquí es una consulta local sobre tablas indexadas, y cachearla solo añadiría
     * una ventana en la que este servicio no ve una versión recién publicada.
     */
    @Override
    public Long estadoInicial(String maquina) {
        List<Long> ids = jdbc.query(
                "SELECT s.id_state AS estado_id " + estadosVigentes
                        + "AND m.code = ? AND m.status = 'PUBLISHED' AND s.is_initial = 1",
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

    @Override
    public List<EstadoMotor> estadosDe(String maquina) {
        return jdbc.query(
                "SELECT s.id_state AS estado_id, s.code AS estado, s.name AS estado_nombre, "
                        + "       s.description AS estado_descripcion, " + COLOR + " AS color, "
                        + "       " + ICONO + " AS icono, s.is_initial AS es_inicial, "
                        + "       s.is_final AS es_final, s.sort_order AS orden "
                        + estadosVigentes
                        + "AND m.code = ? AND m.status = 'PUBLISHED' "
                        + "ORDER BY s.sort_order, s.code",
                (rs, n) -> new EstadoMotor(
                        rs.getLong("estado_id"),
                        rs.getString("estado"),
                        rs.getString("estado_nombre"),
                        rs.getString("estado_descripcion"),
                        rs.getString("color"),
                        rs.getString("icono"),
                        rs.getBoolean("es_inicial"),
                        rs.getBoolean("es_final"),
                        rs.getInt("orden")),
                maquina);
    }

    /**
     * Se descartan las transiciones con reglas o permiso configurados, por el mismo
     * motivo que {@code moverAEstado} las rechaza: ofrecerlas en la UI sería prometer
     * algo que esta vía no puede cumplir.
     */
    @Override
    public List<TransicionMotor> transicionesDe(String maquina) {
        return jdbc.query(
                columnasTransicion() + transicionesVigentes
                        + "AND m.code = ? AND m.status = 'PUBLISHED' "
                        + "  AND tr.permission_code IS NULL "
                        + "  AND NOT EXISTS (SELECT 1 FROM " + esquema + ".transition_rule r "
                        + "                  WHERE r.state_transition_id = tr.id_state_transition "
                        + "                    AND r.deleted = 0 AND r.active = 1) "
                        + "ORDER BY sf.id_state, s.name",
                (rs, n) -> new TransicionMotor(
                        rs.getLong("desde_id"),
                        rs.getString("desde"),
                        rs.getLong("hasta_id"),
                        rs.getString("hasta"),
                        rs.getString("hasta_nombre"),
                        rs.getString("hasta_color"),
                        rs.getBoolean("hasta_es_final"),
                        rs.getString("accion"),
                        rs.getString("accion_nombre"),
                        rs.getBoolean("exige_motivo")),
                maquina);
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
                        + "SELECT m.id_state_machine, t.id_entity_type, ?, s.id_state, 0, NOW(6), 0, NOW(6) "
                        + estadosVigentes
                        + "  AND m.code = ? AND t.code = ? "
                        + "  AND m.status = 'PUBLISHED' AND s.is_initial = 1 "
                        + "  AND NOT EXISTS ( "
                        + "      SELECT 1 FROM " + esquema + ".state_machine_instance i "
                        + "      WHERE i.state_machine_id = m.id_state_machine "
                        + "        AND i.entity_type_id = t.id_entity_type "
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
                        + "JOIN " + esquema + ".state_machine_state s "
                        + "       ON s.id_state = i.current_state_id AND s.state_machine_id = i.state_machine_id "
                        + "JOIN " + esquema + ".state_machine m ON m.id_state_machine = i.state_machine_id "
                        + "JOIN " + esquema + ".entity_type t ON t.id_entity_type = m.entity_type_id "
                        + "WHERE s.deleted = 0 AND m.deleted = 0 AND t.deleted = 0 "
                        + "  AND m.code = ? AND t.code = ? AND i.entity_id = ?",
                maquina, tipoEntidad, entidadId);

        log.info("Ciclo arrancado: {} {} en '{}'", tipoEntidad, entidadId, maquina);
    }

    private boolean existeInstancia(String maquina, String tipoEntidad, Long entidadId) {
        Integer n = jdbc.queryForObject(
                "SELECT COUNT(*) FROM " + esquema + ".state_machine_instance i "
                        + "JOIN " + esquema + ".state_machine_state s "
                        + "       ON s.id_state = i.current_state_id AND s.state_machine_id = i.state_machine_id "
                        + "JOIN " + esquema + ".state_machine m ON m.id_state_machine = i.state_machine_id "
                        + "JOIN " + esquema + ".entity_type t ON t.id_entity_type = m.entity_type_id "
                        + "WHERE s.deleted = 0 AND m.deleted = 0 AND t.deleted = 0 "
                        + "  AND m.code = ? AND t.code = ? AND i.entity_id = ?",
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
                "SELECT s.id_state AS estado_id, s.code AS estado, s.is_final AS es_final "
                        + estadosVigentes
                        + "AND m.code = ? AND m.status = 'PUBLISHED' AND s.id_state = ?",
                (rs, n) -> new Estado(rs.getLong("estado_id"), rs.getString("estado"), rs.getBoolean("es_final")),
                maquina, estadoId).stream().findFirst();
    }

    private String codigosDe(String maquina) {
        List<String> codigos = jdbc.query(
                "SELECT s.code AS estado " + estadosVigentes
                        + "AND m.code = ? AND m.status = 'PUBLISHED' ORDER BY s.sort_order",
                (rs, n) -> rs.getString("estado"), maquina);
        return codigos.isEmpty() ? "(ninguno)" : String.join(", ", codigos);
    }

    private Optional<Instancia> buscarInstancia(String maquina, String tipoEntidad, Long entidadId) {
        return jdbc.query(
                "SELECT i.id_instance, i.current_state_id, i.version, "
                        + "       s.code AS estado, s.is_final AS es_final "
                        + "FROM " + esquema + ".state_machine_instance i "
                        + "JOIN " + esquema + ".state_machine_state s "
                        + "       ON s.id_state = i.current_state_id AND s.state_machine_id = i.state_machine_id "
                        + "JOIN " + esquema + ".state_machine m ON m.id_state_machine = i.state_machine_id "
                        + "JOIN " + esquema + ".entity_type t ON t.id_entity_type = m.entity_type_id "
                        + "WHERE s.deleted = 0 AND m.deleted = 0 AND t.deleted = 0 "
                        + "  AND m.code = ? AND t.code = ? AND i.entity_id = ? "
                        + "  AND m.status = 'PUBLISHED'",
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
                columnasTransicion() + transicionesVigentes
                        + "AND m.code = ? AND m.status = 'PUBLISHED' AND sf.id_state = ? "
                        + "ORDER BY a.code",
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

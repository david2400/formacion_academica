package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.ContextoAcademicoFeedbackPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Lectura de clases, membresías y asistencia con SQL directo (solo lectura).
 *
 * <p>Por qué SQL y no los repositorios JPA de esas entidades:
 * estudiantes_grupo es de estructura_institucion y el proyecto no comparte
 * entidades JPA entre módulos; además {@code ClaseEntity} carga docentes y
 * observaciones en EAGER, demasiado para listar decenas de clases.
 *
 * <p>Reglas de elegibilidad (una sola definición, usada por la bandeja y al responder):
 * <ul>
 *   <li>La clase no está eliminada ni CANCELADA.</li>
 *   <li>Alcance por grupo: el estudiante pertenece al grupo de la clase con una
 *       asignación no eliminada, iniciada antes de la clase y no RETIRADA.</li>
 *   <li>Alcance por clase: lo anterior, o asistencia registrada en la clase (cubre
 *       clases sin grupo formal).</li>
 *   <li>Si la aplicación exige "solo asistentes": asistencia presente.</li>
 * </ul>
 */
@Component
public class ContextoAcademicoFeedbackJdbcAdapter implements ContextoAcademicoFeedbackPort {

    private static final String CLASE_VIGENTE =
            " c.eliminado = 0 AND (c.estado IS NULL OR c.estado <> 'CANCELADA') ";

    private static final String MEMBRESIA = """
             EXISTS (SELECT 1 FROM estudiantes_grupo eg
                     WHERE eg.grupo_id = c.grupo_id AND eg.estudiante_id = :est AND eg.eliminado = 0
                       AND eg.fecha_asignacion <= DATE(c.fecha_inicio) %s)
            """;

    private static final String ASISTIO = """
             EXISTS (SELECT 1 FROM asistencias s
                     WHERE s.clase_id = c.id AND s.estudiante_id = :est AND s.eliminado = 0 %s)
            """;

    private static final String SELECT_CANDIDATA =
            "SELECT fa.id AS assignment_id, c.id AS clase_id, c.nombre, c.fecha_inicio, c.fecha_fin, c.grupo_id ";

    private final NamedParameterJdbcTemplate jdbc;

    public ContextoAcademicoFeedbackJdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    @Override
    public Optional<ClaseInfo> clase(Long claseId) {
        List<ClaseInfo> filas = jdbc.query("""
                        SELECT c.id, c.nombre, c.fecha_inicio, c.fecha_fin, c.grupo_id, c.tipo_clase_id, c.estado
                        FROM clases c WHERE c.id = :id AND c.eliminado = 0
                        """,
                new MapSqlParameterSource("id", claseId),
                (rs, n) -> new ClaseInfo(rs.getLong("id"), rs.getString("nombre"),
                        fecha(rs, "fecha_inicio"), fecha(rs, "fecha_fin"),
                        nullableLong(rs, "grupo_id"), nullableLong(rs, "tipo_clase_id"),
                        "CANCELADA".equals(rs.getString("estado")), List.of()));
        return filas.stream().findFirst().map(c -> new ClaseInfo(c.id(), c.nombre(), c.inicio(), c.fin(), c.grupoId(),
                c.tipoClaseId(), c.cancelada(), profesoresDeClases(List.of(c.id())).getOrDefault(c.id(), List.of())));
    }

    @Override
    public boolean existeGrupo(Long grupoId) {
        Long n = jdbc.queryForObject("SELECT COUNT(*) FROM grupos WHERE id = :id AND eliminado = 0",
                new MapSqlParameterSource("id", grupoId), Long.class);
        return n != null && n > 0;
    }

    @Override
    public Map<Long, List<Long>> profesoresDeClases(Collection<Long> claseIds) {
        Map<Long, List<Long>> resultado = new HashMap<>();
        if (claseIds.isEmpty()) {
            return resultado;
        }
        jdbc.query("SELECT clase_id, profesor_id FROM clase_profesores WHERE clase_id IN (:ids)",
                new MapSqlParameterSource("ids", claseIds),
                rs -> {
                    resultado.computeIfAbsent(rs.getLong("clase_id"), k -> new ArrayList<>()).add(rs.getLong("profesor_id"));
                });
        return resultado;
    }

    @Override
    public List<Candidata> candidatas(Long estudianteId, List<Long> estadosAplicacion, List<Long> estadosMembresiaExcluidos,
                                      LocalDateTime finDesde, LocalDateTime finHasta) {
        if (estadosAplicacion.isEmpty()) {
            return List.of();
        }
        MapSqlParameterSource p = params(estudianteId, estadosMembresiaExcluidos)
                .addValue("estados", estadosAplicacion)
                .addValue("finDesde", finDesde)
                .addValue("finHasta", finHasta);
        String filtroAplicacion = " fa.eliminado = 0 AND fa.estado_id IN (:estados) AND c.fecha_fin BETWEEN :finDesde AND :finHasta ";
        String sql = porClase(filtroAplicacion, estadosMembresiaExcluidos)
                + " UNION ALL "
                + porGrupo(filtroAplicacion, estadosMembresiaExcluidos)
                + " ORDER BY fecha_fin DESC";
        return jdbc.query(sql, p, this::candidata);
    }

    @Override
    public Optional<Candidata> candidata(Long estudianteId, Long assignmentId, Long claseId, List<Long> estadosMembresiaExcluidos) {
        MapSqlParameterSource p = params(estudianteId, estadosMembresiaExcluidos)
                .addValue("aid", assignmentId)
                .addValue("cid", claseId);
        String filtro = " fa.eliminado = 0 AND fa.id = :aid AND c.id = :cid ";
        String sql = porClase(filtro, estadosMembresiaExcluidos) + " UNION ALL " + porGrupo(filtro, estadosMembresiaExcluidos);
        return jdbc.query(sql, p, this::candidata).stream().findFirst();
    }

    // ─── SQL ─────────────────────────────────────────────────────────────────

    private static String porClase(String filtro, List<Long> excluidos) {
        return SELECT_CANDIDATA
                + "FROM feedback_assignments fa JOIN clases c ON c.id = fa.clase_id "
                + "WHERE fa.clase_id IS NOT NULL AND " + filtro + " AND " + CLASE_VIGENTE
                + " AND (" + membresia(excluidos) + " OR " + asistio(false) + ")"
                + " AND (fa.solo_asistentes = 0 OR " + asistio(true) + ")";
    }

    private static String porGrupo(String filtro, List<Long> excluidos) {
        // Rango por fecha_inicio sin DATE() sobre la columna: aprovecha idx_clases_grupo_fecha.
        return SELECT_CANDIDATA
                + "FROM feedback_assignments fa JOIN clases c ON c.grupo_id = fa.grupo_id "
                + " AND c.fecha_inicio >= fa.desde AND c.fecha_inicio < DATE_ADD(fa.hasta, INTERVAL 1 DAY) "
                + "WHERE fa.grupo_id IS NOT NULL AND " + filtro + " AND " + CLASE_VIGENTE
                + " AND " + membresia(excluidos)
                + " AND (fa.solo_asistentes = 0 OR " + asistio(true) + ")";
    }

    private static String membresia(List<Long> excluidos) {
        return MEMBRESIA.formatted(excluidos.isEmpty() ? "" : "AND eg.estado_id NOT IN (:excluidos)");
    }

    private static String asistio(boolean presente) {
        return ASISTIO.formatted(presente ? "AND s.presente = 1" : "");
    }

    private static MapSqlParameterSource params(Long estudianteId, List<Long> excluidos) {
        MapSqlParameterSource p = new MapSqlParameterSource("est", estudianteId);
        if (!excluidos.isEmpty()) {
            p.addValue("excluidos", excluidos);
        }
        return p;
    }

    private Candidata candidata(ResultSet rs, int n) throws SQLException {
        return new Candidata(rs.getLong("assignment_id"), rs.getLong("clase_id"), rs.getString("nombre"),
                fecha(rs, "fecha_inicio"), fecha(rs, "fecha_fin"), nullableLong(rs, "grupo_id"));
    }

    static LocalDateTime fecha(ResultSet rs, String columna) throws SQLException {
        Timestamp ts = rs.getTimestamp(columna);
        return ts == null ? null : ts.toLocalDateTime();
    }

    static Long nullableLong(ResultSet rs, String columna) throws SQLException {
        long v = rs.getLong(columna);
        return rs.wasNull() ? null : v;
    }
}

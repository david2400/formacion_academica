package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResultsQueryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.ComentarioFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.FiltroResultadosFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.PuntoTendenciaFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.ResultadoClaseFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results.ResultadoCriterioFeedbackDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.List;

import static com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter.ContextoAcademicoFeedbackJdbcAdapter.fecha;
import static com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter.ContextoAcademicoFeedbackJdbcAdapter.nullableLong;

/**
 * Reportes de feedback con SQL agregado (AVG/COUNT/GROUP BY) y paginación en
 * la base de datos. Ninguna consulta trae respuestas individuales salvo
 * /comentarios, que es paginada.
 *
 * <p>Convenciones:
 * <ul>
 *   <li>El "período" filtra por la FECHA DE LA CLASE (c.fecha_inicio), no por la
 *       del envío: en formularios anónimos la del envío está truncada.</li>
 *   <li>Los índices se devuelven en 0..1 (promedio de valor_normalizado); el
 *       servicio los lleva a 0..100 y aplica el umbral de anonimato.</li>
 *   <li>El filtro por docente usa EXISTS sobre clase_profesores (una clase con
 *       dos docentes no duplica respuestas).</li>
 * </ul>
 */
@Component
public class FeedbackResultsJdbcAdapter implements FeedbackResultsQueryPort {

    private static final String BASE_RESPUESTAS = """
            FROM feedback_responses r
            JOIN clases c ON c.id = r.clase_id
            JOIN feedback_assignments fa ON fa.id = r.assignment_id
            """;

    private final NamedParameterJdbcTemplate jdbc;

    public FeedbackResultsJdbcAdapter(JdbcTemplate jdbcTemplate) {
        this.jdbc = new NamedParameterJdbcTemplate(jdbcTemplate);
    }

    // ─── Totales ─────────────────────────────────────────────────────────────

    @Override
    public Totales totales(FiltroResultadosFeedbackDto f) {
        Condiciones w = condiciones(f, "r.assignment_id", "r.form_id");
        String sql = "SELECT COUNT(DISTINCT r.id) AS n, AVG(a.valor_normalizado) AS idx "
                + BASE_RESPUESTAS
                + " LEFT JOIN feedback_answers a ON a.response_id = r.id "
                + criterioJoin(f, "a")
                + w.where();
        return jdbc.queryForObject(sql, w.params(), (rs, n) -> new Totales(rs.getLong("n"), rs.getBigDecimal("idx")));
    }

    @Override
    public long participaciones(FiltroResultadosFeedbackDto f) {
        Condiciones w = condiciones(f, "p.assignment_id", "fa.form_id");
        String sql = """
                SELECT COUNT(*) FROM feedback_participations p
                JOIN clases c ON c.id = p.clase_id
                JOIN feedback_assignments fa ON fa.id = p.assignment_id
                """ + w.where() + " AND p.numero = 1";
        Long n = jdbc.queryForObject(sql, w.params(), Long.class);
        return n == null ? 0 : n;
    }

    @Override
    public long elegibles(FiltroResultadosFeedbackDto f, List<Long> estadosAplicacion, List<Long> excluidos) {
        if (estadosAplicacion.isEmpty()) {
            return 0;
        }
        Condiciones w = condiciones(f, "fa.id", "fa.form_id");
        w.params().addValue("estados", estadosAplicacion);
        String comun = w.where() + " AND fa.eliminado = 0 AND fa.estado_id IN (:estados)"
                + " AND c.eliminado = 0 AND (c.estado IS NULL OR c.estado <> 'CANCELADA') AND c.fecha_fin <= NOW()";
        String alcance = "SELECT fa.id AS aid, c.id AS cid, c.grupo_id, c.fecha_inicio, fa.solo_asistentes ";
        String exclusion = "";
        if (!excluidos.isEmpty()) {
            w.params().addValue("excluidos", excluidos);
            exclusion = " AND eg.estado_id NOT IN (:excluidos)";
        }
        String sql = "SELECT COUNT(*) FROM ("
                + alcance + "FROM feedback_assignments fa JOIN clases c ON c.id = fa.clase_id " + comun
                + " UNION ALL "
                + alcance + "FROM feedback_assignments fa JOIN clases c ON c.grupo_id = fa.grupo_id"
                + "  AND c.fecha_inicio >= fa.desde AND c.fecha_inicio < DATE_ADD(fa.hasta, INTERVAL 1 DAY) " + comun
                + ") x JOIN estudiantes_grupo eg ON eg.grupo_id = x.grupo_id AND eg.eliminado = 0"
                + "  AND eg.fecha_asignacion <= DATE(x.fecha_inicio)" + exclusion
                + " WHERE x.solo_asistentes = 0 OR EXISTS (SELECT 1 FROM asistencias s WHERE s.clase_id = x.cid"
                + "  AND s.estudiante_id = eg.estudiante_id AND s.presente = 1 AND s.eliminado = 0)";
        Long n = jdbc.queryForObject(sql, w.params(), Long.class);
        return n == null ? 0 : n;
    }

    @Override
    public int umbralAnonimato(FiltroResultadosFeedbackDto f) {
        Condiciones w = condiciones(f, "r.assignment_id", "r.form_id");
        String sql = "SELECT COALESCE(MAX(fa2.umbral_anonimato), 0) FROM feedback_assignments fa2"
                + " JOIN feedback_forms fo ON fo.id = fa2.form_id AND fo.anonimo = 1"
                + " WHERE fa2.id IN (SELECT DISTINCT r.assignment_id " + BASE_RESPUESTAS + w.where() + ")";
        Integer k = jdbc.queryForObject(sql, w.params(), Integer.class);
        return k == null ? 0 : k;
    }

    // ─── Por pregunta / criterio ─────────────────────────────────────────────

    @Override
    public List<AgregadoPregunta> porPregunta(FiltroResultadosFeedbackDto f) {
        Condiciones w = condiciones(f, "r.assignment_id", "r.form_id");
        String sql = "SELECT a.question_id, COUNT(DISTINCT a.response_id) AS n, AVG(a.valor_numerico) AS prom,"
                + " AVG(a.valor_normalizado) AS idx "
                + BASE_RESPUESTAS + " JOIN feedback_answers a ON a.response_id = r.id "
                + w.where() + " GROUP BY a.question_id";
        return jdbc.query(sql, w.params(), (rs, n) -> new AgregadoPregunta(rs.getLong("question_id"),
                rs.getLong("n"), rs.getBigDecimal("prom"), rs.getBigDecimal("idx")));
    }

    @Override
    public List<ConteoDistribucion> distribucion(FiltroResultadosFeedbackDto f) {
        Condiciones w = condiciones(f, "r.assignment_id", "r.form_id");
        String sql = "SELECT a.question_id, a.option_id, a.valor_numerico, COUNT(*) AS n "
                + BASE_RESPUESTAS + " JOIN feedback_answers a ON a.response_id = r.id "
                + w.where() + " AND a.texto IS NULL GROUP BY a.question_id, a.option_id, a.valor_numerico";
        return jdbc.query(sql, w.params(), (rs, n) -> new ConteoDistribucion(rs.getLong("question_id"),
                nullableLong(rs, "option_id"), rs.getBigDecimal("valor_numerico"), rs.getLong("n")));
    }

    /** Ordenado de menor a mayor índice: lo que más requiere atención, primero. */
    @Override
    public List<ResultadoCriterioFeedbackDto> porCriterio(FiltroResultadosFeedbackDto f) {
        Condiciones w = condiciones(f, "r.assignment_id", "r.form_id");
        String sql = "SELECT cr.id, cr.codigo, cr.nombre, COUNT(DISTINCT a.response_id) AS n, AVG(a.valor_normalizado) AS idx "
                + BASE_RESPUESTAS
                + " JOIN feedback_answers a ON a.response_id = r.id AND a.valor_normalizado IS NOT NULL"
                + " JOIN feedback_questions q ON q.id = a.question_id"
                + " JOIN feedback_criteria cr ON cr.id = q.criterion_id"
                + w.where() + (f.getCriterionId() != null ? " AND cr.id = :criterionId" : "")
                + " GROUP BY cr.id, cr.codigo, cr.nombre ORDER BY idx ASC";
        if (f.getCriterionId() != null) {
            w.params().addValue("criterionId", f.getCriterionId());
        }
        return jdbc.query(sql, w.params(), (rs, n) -> ResultadoCriterioFeedbackDto.builder()
                .criterionId(rs.getLong("id"))
                .codigo(rs.getString("codigo"))
                .nombre(rs.getString("nombre"))
                .respuestas(rs.getLong("n"))
                .indice(rs.getBigDecimal("idx"))
                .build());
    }

    @Override
    public List<PuntoTendenciaFeedbackDto> tendencia(FiltroResultadosFeedbackDto f, Granularidad granularidad) {
        Condiciones w = condiciones(f, "r.assignment_id", "r.form_id");
        String periodo = granularidad == Granularidad.SEMANA
                ? "CONCAT(YEAR(c.fecha_inicio), '-W', LPAD(WEEK(c.fecha_inicio, 3), 2, '0'))"
                : "DATE_FORMAT(c.fecha_inicio, '%Y-%m')";
        String sql = "SELECT " + periodo + " AS periodo, COUNT(DISTINCT r.id) AS n, AVG(a.valor_normalizado) AS idx "
                + BASE_RESPUESTAS + " LEFT JOIN feedback_answers a ON a.response_id = r.id "
                + criterioJoin(f, "a")
                + w.where() + " GROUP BY periodo ORDER BY periodo";
        return jdbc.query(sql, w.params(), (rs, n) -> PuntoTendenciaFeedbackDto.builder()
                .periodo(rs.getString("periodo"))
                .respuestas(rs.getLong("n"))
                .indice(rs.getBigDecimal("idx"))
                .build());
    }

    // ─── Por clase / comentarios (paginados en SQL) ──────────────────────────

    @Override
    public Page<FilaClase> porClase(FiltroResultadosFeedbackDto f, Pageable pageable) {
        Condiciones w = condiciones(f, "r.assignment_id", "r.form_id");
        String orden = ordenClases(pageable.getSort());
        String sql = "SELECT r.assignment_id, c.id AS clase_id, c.nombre, c.fecha_inicio, c.grupo_id,"
                + " COUNT(DISTINCT r.id) AS n, AVG(a.valor_normalizado) AS idx, fo.anonimo, fa.umbral_anonimato "
                + BASE_RESPUESTAS
                + " JOIN feedback_forms fo ON fo.id = r.form_id"
                + " LEFT JOIN feedback_answers a ON a.response_id = r.id "
                + w.where()
                + " GROUP BY r.assignment_id, c.id, c.nombre, c.fecha_inicio, c.grupo_id, fo.anonimo, fa.umbral_anonimato"
                + " ORDER BY " + orden + " LIMIT :limite OFFSET :desplazamiento";
        String conteo = "SELECT COUNT(*) FROM (SELECT DISTINCT r.assignment_id, r.clase_id "
                + BASE_RESPUESTAS + w.where() + ") t";
        Long total = jdbc.queryForObject(conteo, w.params(), Long.class);
        w.params().addValue("limite", pageable.getPageSize()).addValue("desplazamiento", pageable.getOffset());
        List<FilaClase> filas = jdbc.query(sql, w.params(), (rs, n) -> new FilaClase(
                ResultadoClaseFeedbackDto.builder()
                        .assignmentId(rs.getLong("assignment_id"))
                        .claseId(rs.getLong("clase_id"))
                        .claseNombre(rs.getString("nombre"))
                        .claseInicio(fecha(rs, "fecha_inicio"))
                        .grupoId(nullableLong(rs, "grupo_id"))
                        .respuestas(rs.getLong("n"))
                        .indice(rs.getBigDecimal("idx"))
                        .build(),
                rs.getBoolean("anonimo"),
                rs.getInt("umbral_anonimato")));
        return new PageImpl<>(filas, pageable, total == null ? 0 : total);
    }

    /**
     * Comentarios abiertos, más recientes primero. En formularios anónimos se
     * excluyen los de (aplicación, clase) con menos respuestas que el umbral.
     */
    @Override
    public Page<ComentarioFeedbackDto> comentarios(FiltroResultadosFeedbackDto f, Pageable pageable) {
        Condiciones w = condiciones(f, "r.assignment_id", "r.form_id");
        String desde = BASE_RESPUESTAS
                + " JOIN feedback_forms fo ON fo.id = r.form_id"
                + " JOIN feedback_answers a ON a.response_id = r.id AND a.texto IS NOT NULL"
                + " JOIN feedback_questions q ON q.id = a.question_id "
                + w.where()
                + " AND (fo.anonimo = 0 OR (SELECT COUNT(*) FROM feedback_responses r2"
                + "      WHERE r2.assignment_id = r.assignment_id AND r2.clase_id = r.clase_id) >= fa.umbral_anonimato)";
        Long total = jdbc.queryForObject("SELECT COUNT(*) " + desde, w.params(), Long.class);
        w.params().addValue("limite", pageable.getPageSize()).addValue("desplazamiento", pageable.getOffset());
        List<ComentarioFeedbackDto> filas = jdbc.query(
                "SELECT a.question_id, q.enunciado, a.texto, c.id AS clase_id, c.nombre, DATE(r.enviado_en) AS fecha "
                        + desde + " ORDER BY r.enviado_en DESC, a.id DESC LIMIT :limite OFFSET :desplazamiento",
                w.params(),
                (rs, n) -> {
                    Date fecha = rs.getDate("fecha");
                    return ComentarioFeedbackDto.builder()
                            .preguntaId(rs.getLong("question_id"))
                            .enunciado(rs.getString("enunciado"))
                            .texto(rs.getString("texto"))
                            .claseId(rs.getLong("clase_id"))
                            .claseNombre(rs.getString("nombre"))
                            .fecha(fecha == null ? null : fecha.toLocalDate())
                            .build();
                });
        return new PageImpl<>(filas, pageable, total == null ? 0 : total);
    }

    // ─── Filtros ─────────────────────────────────────────────────────────────

    record Condiciones(String where, MapSqlParameterSource params) {
    }

    /**
     * WHERE común a todos los reportes. Las columnas de aplicación y formulario
     * varían según la tabla base (respuestas o participaciones); clase siempre es {@code c}.
     */
    static Condiciones condiciones(FiltroResultadosFeedbackDto f, String colAplicacion, String colFormulario) {
        StringBuilder sql = new StringBuilder(" WHERE 1 = 1");
        MapSqlParameterSource p = new MapSqlParameterSource();
        if (f.getAssignmentId() != null) {
            sql.append(" AND ").append(colAplicacion).append(" = :assignmentId");
            p.addValue("assignmentId", f.getAssignmentId());
        }
        if (f.getFormId() != null) {
            sql.append(" AND ").append(colFormulario).append(" = :formId");
            p.addValue("formId", f.getFormId());
        }
        if (f.getClaseId() != null) {
            sql.append(" AND c.id = :claseId");
            p.addValue("claseId", f.getClaseId());
        }
        if (f.getGrupoId() != null) {
            sql.append(" AND c.grupo_id = :grupoId");
            p.addValue("grupoId", f.getGrupoId());
        }
        if (f.getTipoClaseId() != null) {
            sql.append(" AND c.tipo_clase_id = :tipoClaseId");
            p.addValue("tipoClaseId", f.getTipoClaseId());
        }
        if (f.getProfesorId() != null) {
            sql.append(" AND EXISTS (SELECT 1 FROM clase_profesores cp WHERE cp.clase_id = c.id AND cp.profesor_id = :profesorId)");
            p.addValue("profesorId", f.getProfesorId());
        }
        if (f.getDesde() != null) {
            sql.append(" AND c.fecha_inicio >= :desde");
            p.addValue("desde", f.getDesde());
        }
        if (f.getHasta() != null) {
            sql.append(" AND c.fecha_inicio < :hastaExclusivo");
            p.addValue("hastaExclusivo", f.getHasta().plusDays(1));
        }
        return new Condiciones(sql.toString(), p);
    }

    /** Si se filtra por criterio, solo cuentan las respuestas a preguntas de ese criterio. */
    private static String criterioJoin(FiltroResultadosFeedbackDto f, String aliasRespuesta) {
        if (f.getCriterionId() == null) {
            return "";
        }
        return " AND " + aliasRespuesta + ".question_id IN (SELECT q2.id FROM feedback_questions q2 WHERE q2.criterion_id = "
                + f.getCriterionId().longValue() + ") ";
    }

    /** Lista blanca de ordenamientos (nunca se interpola texto del cliente). */
    private static String ordenClases(Sort sort) {
        Sort.Order o = sort == null ? null : sort.getOrderFor("indice");
        if (o != null) {
            return "idx " + (o.isAscending() ? "ASC" : "DESC") + ", c.fecha_inicio DESC";
        }
        Sort.Order r = sort == null ? null : sort.getOrderFor("respuestas");
        if (r != null) {
            return "n " + (r.isAscending() ? "ASC" : "DESC") + ", c.fecha_inicio DESC";
        }
        return "c.fecha_inicio DESC";
    }
}

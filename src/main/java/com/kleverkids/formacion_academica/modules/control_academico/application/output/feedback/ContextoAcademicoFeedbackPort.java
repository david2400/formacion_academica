package com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.List;
import java.util.Optional;

/**
 * Lectura del contexto académico que el feedback necesita y que vive en otras
 * tablas: clases (y sus docentes), membresía estudiante-grupo y asistencia.
 *
 * <p>Es de solo lectura y se implementa con SQL directo: estudiantes_grupo
 * pertenece a estructura_institucion y el proyecto no comparte entidades JPA
 * entre módulos (mismo criterio que CuentaUsuarioJdbcAdapter). Si mañana esas
 * tablas se mueven a otro servicio, solo cambia el adapter.
 */
public interface ContextoAcademicoFeedbackPort {

    Optional<ClaseInfo> clase(Long claseId);

    boolean existeGrupo(Long grupoId);

    /** Docentes de varias clases en una sola consulta (clase_id → profesores). */
    Map<Long, List<Long>> profesoresDeClases(Collection<Long> claseIds);

    /**
     * Pares (aplicación, clase) que aplican al estudiante: aplicaciones en los
     * estados dados cuyo alcance incluye clases de sus grupos, no canceladas,
     * que terminan entre {@code finDesde} y {@code finHasta}, y en las que es
     * elegible (membresía vigente a la fecha de la clase y, si la aplicación lo
     * exige, asistencia presente).
     */
    List<Candidata> candidatas(Long estudianteId, List<Long> estadosAplicacion,
                               List<Long> estadosMembresiaExcluidos,
                               LocalDateTime finDesde, LocalDateTime finHasta);

    /** Igual que {@link #candidatas} pero para un único par: valida elegibilidad al responder. */
    Optional<Candidata> candidata(Long estudianteId, Long assignmentId, Long claseId,
                                  List<Long> estadosMembresiaExcluidos);

    record ClaseInfo(Long id, String nombre, LocalDateTime inicio, LocalDateTime fin,
                     Long grupoId, Long tipoClaseId, boolean cancelada, List<Long> profesoresIds) {
    }

    record Candidata(Long assignmentId, Long claseId, String claseNombre,
                     LocalDateTime claseInicio, LocalDateTime claseFin, Long grupoId) {
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Filtros comunes de todos los reportes. {@code profesorId} lo fuerza el
 * controlador al del token cuando quien consulta es un docente.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroResultadosFeedbackDto {

    private Long assignmentId;
    private Long formId;
    private Long claseId;
    private Long grupoId;
    private Long profesorId;
    private Long tipoClaseId;
    private Long criterionId;
    private LocalDate desde;
    private LocalDate hasta;
}

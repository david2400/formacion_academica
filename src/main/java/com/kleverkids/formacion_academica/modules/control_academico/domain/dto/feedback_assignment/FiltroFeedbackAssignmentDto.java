package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Filtros del listado de aplicaciones. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FiltroFeedbackAssignmentDto {

    private Long formId;
    private Long claseId;
    private Long grupoId;
    private Long estadoId;
}

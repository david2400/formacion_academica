package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_form.CambiarEstadoFeedbackDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;

public interface CambiarEstadoFeedbackAssignmentUseCase {
    FeedbackAssignment cambiarEstado(Long assignmentId, CambiarEstadoFeedbackDto dto);
}

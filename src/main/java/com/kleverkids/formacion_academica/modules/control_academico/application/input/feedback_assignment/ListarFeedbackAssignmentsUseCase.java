package com.kleverkids.formacion_academica.modules.control_academico.application.input.feedback_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment.FiltroFeedbackAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ListarFeedbackAssignmentsUseCase {
    Page<FeedbackAssignment> listar(FiltroFeedbackAssignmentDto filtro, Pageable pageable);
}

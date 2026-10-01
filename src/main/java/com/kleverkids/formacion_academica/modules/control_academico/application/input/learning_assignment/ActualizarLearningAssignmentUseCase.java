package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.ActualizarLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;

public interface ActualizarLearningAssignmentUseCase {

    LearningAssignment actualizar(ActualizarLearningAssignmentDto request);
}

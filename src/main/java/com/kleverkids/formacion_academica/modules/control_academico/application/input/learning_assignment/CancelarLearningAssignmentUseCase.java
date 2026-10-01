package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;

public interface CancelarLearningAssignmentUseCase {

    LearningAssignment cancelar(Long id);
}

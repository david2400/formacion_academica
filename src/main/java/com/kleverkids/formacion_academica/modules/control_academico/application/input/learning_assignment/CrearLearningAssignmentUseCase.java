package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.CrearLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;

public interface CrearLearningAssignmentUseCase {

    LearningAssignment crear(CrearLearningAssignmentDto request);
}

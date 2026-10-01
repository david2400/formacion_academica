package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.FiltroLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;

import java.util.List;

public interface ListarLearningAssignmentUseCase {

    List<LearningAssignment> listar(FiltroLearningAssignmentDto filtro);
}

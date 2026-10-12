package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.ListarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.FiltroLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarLearningAssignmentService implements ListarLearningAssignmentUseCase {

    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;

    @Override
    public List<LearningAssignment> listar(FiltroLearningAssignmentDto filtro) {
        return learningAssignmentRepositoryPort.buscar(filtro != null ? filtro : FiltroLearningAssignmentDto.builder().build());
    }
}

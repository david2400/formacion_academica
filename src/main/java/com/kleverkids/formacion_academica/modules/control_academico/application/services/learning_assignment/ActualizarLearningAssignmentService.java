package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.ActualizarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.ActualizarLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarLearningAssignmentService implements ActualizarLearningAssignmentUseCase {

    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;

    @Override
    public LearningAssignment actualizar(ActualizarLearningAssignmentDto request) {
        return learningAssignmentRepositoryPort.actualizar(request);
    }
}

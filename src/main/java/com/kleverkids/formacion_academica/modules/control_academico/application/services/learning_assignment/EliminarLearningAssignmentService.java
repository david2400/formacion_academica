package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.EliminarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarLearningAssignmentService implements EliminarLearningAssignmentUseCase {

    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;

    @Override
    public void eliminar(Long id) {
        learningAssignmentRepositoryPort.eliminar(id);
    }
}

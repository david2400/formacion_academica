package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.ConsultarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarLearningAssignmentService implements ConsultarLearningAssignmentUseCase {

    private final LearningAssignmentSoporte learningAssignmentSoporte;

    @Override
    public LearningAssignment consultarPorId(Long id) {
        return learningAssignmentSoporte.obtenerOFallar(id);
    }
}

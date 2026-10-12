package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.CancelarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CancelarLearningAssignmentService implements CancelarLearningAssignmentUseCase {

    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;
    private final LearningAssignmentSoporte learningAssignmentSoporte;

    @Override
    public LearningAssignment cancelar(Long id) {
        learningAssignmentSoporte.obtenerOFallar(id);
        return learningAssignmentRepositoryPort.actualizarEstado(id, EstadoLearningAssignment.CANCELLED);
    }
}

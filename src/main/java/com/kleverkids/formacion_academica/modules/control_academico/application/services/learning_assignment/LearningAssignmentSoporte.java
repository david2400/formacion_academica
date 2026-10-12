package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Lógica compartida por varios casos de uso de learning_assignment (antes métodos privados de
 * LearningAssignmentService). Solo la usan los servicios de este paquete.
 */
@Component
@RequiredArgsConstructor
class LearningAssignmentSoporte {

    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;

    LearningAssignment obtenerOFallar(Long id) {
        return learningAssignmentRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));
    }
}

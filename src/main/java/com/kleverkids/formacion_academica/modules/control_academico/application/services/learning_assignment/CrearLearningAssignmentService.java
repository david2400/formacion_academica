package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.CrearLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.CrearLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.EstadoLearningSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearLearningAssignmentService implements CrearLearningAssignmentUseCase {

    private final LearningAssignmentRepositoryPort learningAssignmentRepositoryPort;
    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;
    private final LearningAssignmentEventPublisher learningAssignmentEventPublisher;

    @Override
    public LearningAssignment crear(CrearLearningAssignmentDto request) {
        if ((request.getGrupoId() == null) == (request.getEstudianteId() == null)) {
            throw new IllegalArgumentException(
                    "Debe indicarse exactamente uno entre grupoId y estudianteId, no ambos ni ninguno");
        }

        LearningSequence sequence = learningSequenceRepositoryPort.obtenerPorId(request.getSequenceId())
                .orElseThrow(() -> new IllegalArgumentException("La secuencia indicada no existe"));
        if (sequence.getEstado() != EstadoLearningSequence.PUBLISHED) {
            throw new IllegalStateException("Solo se pueden asignar secuencias publicadas");
        }

        LearningAssignment asignacion = learningAssignmentRepositoryPort.guardar(request, sequence.getVersionActualId());
        publicarEventoAsignacion(asignacion);
        return asignacion;
    }

    private void publicarEventoAsignacion(LearningAssignment asignacion) {
        asignacion.registrarEventoAsignacion();
        asignacion.getDomainEvents().forEach(learningAssignmentEventPublisher::publish);
        asignacion.clearDomainEvents();
    }
}

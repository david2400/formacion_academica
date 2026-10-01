package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.ActualizarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.CancelarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.ConsultarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.CrearLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.EliminarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_assignment.ListarLearningAssignmentUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.ActualizarLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.CrearLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.FiltroLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.EstadoLearningSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class LearningAssignmentService implements CrearLearningAssignmentUseCase,
        ActualizarLearningAssignmentUseCase,
        ConsultarLearningAssignmentUseCase,
        ListarLearningAssignmentUseCase,
        EliminarLearningAssignmentUseCase,
        CancelarLearningAssignmentUseCase {

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

    @Override
    public LearningAssignment actualizar(ActualizarLearningAssignmentDto request) {
        return learningAssignmentRepositoryPort.actualizar(request);
    }

    @Override
    public LearningAssignment consultarPorId(Long id) {
        return obtenerOFallar(id);
    }

    @Override
    public List<LearningAssignment> listar(FiltroLearningAssignmentDto filtro) {
        return learningAssignmentRepositoryPort.buscar(filtro != null ? filtro : FiltroLearningAssignmentDto.builder().build());
    }

    @Override
    public void eliminar(Long id) {
        learningAssignmentRepositoryPort.eliminar(id);
    }

    @Override
    public LearningAssignment cancelar(Long id) {
        obtenerOFallar(id);
        return learningAssignmentRepositoryPort.actualizarEstado(id, EstadoLearningAssignment.CANCELLED);
    }

    private LearningAssignment obtenerOFallar(Long id) {
        return learningAssignmentRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));
    }

    private void publicarEventoAsignacion(LearningAssignment asignacion) {
        asignacion.registrarEventoAsignacion();
        asignacion.getDomainEvents().forEach(learningAssignmentEventPublisher::publish);
        asignacion.clearDomainEvents();
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_assignment.FiltroFeedbackAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.FeedbackAssignment;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface FeedbackAssignmentRepositoryPort {

    /** Crea la aplicación y arranca su ciclo en el motor. */
    FeedbackAssignment crear(FeedbackAssignment assignment);

    FeedbackAssignment actualizar(FeedbackAssignment assignment);

    Optional<FeedbackAssignment> buscarPorId(Long id);

    List<FeedbackAssignment> buscarPorIds(Collection<Long> ids);

    Page<FeedbackAssignment> listar(FiltroFeedbackAssignmentDto filtro, Pageable pageable);

    FeedbackAssignment cambiarEstado(Long assignmentId, Long nuevoEstadoId, String motivo);

    /** true si la aplicación ya recibió alguna respuesta. */
    boolean tieneRespuestas(Long assignmentId);
}

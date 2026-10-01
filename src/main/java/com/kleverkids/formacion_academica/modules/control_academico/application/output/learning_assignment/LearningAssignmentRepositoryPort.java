package com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.ActualizarLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.CrearLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.FiltroLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;

import java.util.List;
import java.util.Optional;

public interface LearningAssignmentRepositoryPort {

    LearningAssignment guardar(CrearLearningAssignmentDto dto, Long sequenceVersionId);

    Optional<LearningAssignment> obtenerPorId(Long id);

    List<LearningAssignment> buscar(FiltroLearningAssignmentDto filtro);

    LearningAssignment actualizar(ActualizarLearningAssignmentDto dto);

    /** Transición de estado pura (ASSIGNED->IN_PROGRESS->COMPLETED/CANCELLED), sin pasar por los otros campos editables. */
    LearningAssignment actualizarEstado(Long id, com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment estado);

    void eliminar(Long id);
}

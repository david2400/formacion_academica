package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment.LearningAssignmentRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.ActualizarLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.CrearLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_assignment.FiltroLearningAssignmentDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.LearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.LearningAssignmentMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_assignment.LearningAssignmentEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.LearningAssignmentJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.LearningAssignmentSpecifications;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class LearningAssignmentJpaAdapter implements LearningAssignmentRepositoryPort {

    private final LearningAssignmentJpaRepository learningAssignmentJpaRepository;
    private final LearningAssignmentMapper learningAssignmentMapper;

    @Override
    public LearningAssignment guardar(CrearLearningAssignmentDto dto, Long sequenceVersionId) {
        LearningAssignmentEntity entity = learningAssignmentMapper.toEntity(dto);
        entity.setSequenceVersionId(sequenceVersionId);
        entity.setAssignedAt(Instant.now());
        return learningAssignmentMapper.toDomainModel(learningAssignmentJpaRepository.save(entity));
    }

    @Override
    public Optional<LearningAssignment> obtenerPorId(Long id) {
        return learningAssignmentJpaRepository.findById(id).map(learningAssignmentMapper::toDomainModel);
    }

    @Override
    public List<LearningAssignment> buscar(FiltroLearningAssignmentDto filtro) {
        return learningAssignmentMapper.toDomainModelList(
                learningAssignmentJpaRepository.findAll(LearningAssignmentSpecifications.desdeFiltro(filtro)));
    }

    @Override
    public LearningAssignment actualizar(ActualizarLearningAssignmentDto dto) {
        LearningAssignmentEntity existing = buscarEntidad(dto.getId());
        if (dto.getAvailableFrom() != null) {
            existing.setAvailableFrom(learningAssignmentMapper.localDateTimeToInstant(dto.getAvailableFrom()));
        }
        if (dto.getDueAt() != null) {
            existing.setDueAt(learningAssignmentMapper.localDateTimeToInstant(dto.getDueAt()));
        }
        if (dto.getAllowLateSubmission() != null) {
            existing.setAllowLateSubmission(dto.getAllowLateSubmission());
        }
        if (dto.getLockAfterDue() != null) {
            existing.setLockAfterDue(dto.getLockAfterDue());
        }
        return learningAssignmentMapper.toDomainModel(learningAssignmentJpaRepository.save(existing));
    }

    @Override
    public LearningAssignment actualizarEstado(Long id, EstadoLearningAssignment estado) {
        LearningAssignmentEntity existing = buscarEntidad(id);
        existing.setEstado(estado);
        return learningAssignmentMapper.toDomainModel(learningAssignmentJpaRepository.save(existing));
    }

    @Override
    public void eliminar(Long id) {
        LearningAssignmentEntity existing = buscarEntidad(id);
        existing.setEliminado(true);
        learningAssignmentJpaRepository.save(existing);
    }

    private LearningAssignmentEntity buscarEntidad(Long id) {
        return learningAssignmentJpaRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Asignación no encontrada"));
    }
}

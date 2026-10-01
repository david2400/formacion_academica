package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject.EstadoActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ActivityAttemptMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity_attempt.ActivityAttemptAnswerEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity_attempt.ActivityAttemptEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActivityAttemptAnswerJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActivityAttemptJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Component
public class ActivityAttemptJpaAdapter implements ActivityAttemptRepositoryPort {

    private final ActivityAttemptJpaRepository activityAttemptJpaRepository;
    private final ActivityAttemptAnswerJpaRepository activityAttemptAnswerJpaRepository;
    private final ActivityAttemptMapper activityAttemptMapper;

    /**
     * Si {@code attempt.getId()} es null es un intento nuevo (iniciar): se
     * mapea una entidad fresca completa. Si ya tiene id (completar) se
     * actualiza únicamente sobre la entidad existente, mutando solo los
     * campos que el dominio {@code ActivityAttempt} conoce — mapear una
     * entidad nueva desde el dominio pisaría con null los campos de
     * auditoría de {@code AuditInfo} (usrCrea, createdAt) que el dominio no
     * tiene, violando la columna NOT NULL de usrCrea.
     */
    @Override
    public ActivityAttempt guardar(ActivityAttempt attempt) {
        if (attempt.getId() == null) {
            ActivityAttemptEntity entity = activityAttemptMapper.toEntity(attempt);
            return activityAttemptMapper.toDomainModel(activityAttemptJpaRepository.save(entity));
        }
        ActivityAttemptEntity existing = activityAttemptJpaRepository.findById(attempt.getId())
                .orElseThrow(() -> new IllegalArgumentException("Intento no encontrado"));
        existing.setEstado(attempt.getEstado());
        existing.setCompletedAt(activityAttemptMapper.localDateTimeToInstant(attempt.getCompletedAt()));
        existing.setScore(attempt.getScore());
        existing.setMetadata(attempt.getMetadata());
        return activityAttemptMapper.toDomainModel(activityAttemptJpaRepository.save(existing));
    }

    @Override
    public Optional<ActivityAttempt> obtenerPorId(Long id) {
        return activityAttemptJpaRepository.findById(id).map(activityAttemptMapper::toDomainModel);
    }

    @Override
    public Optional<ActivityAttempt> obtenerEnProgreso(Long activityId, Long assignmentId, Long estudianteId) {
        return activityAttemptJpaRepository
                .findEnProgreso(activityId, assignmentId, estudianteId, EstadoActivityAttempt.IN_PROGRESS)
                .map(activityAttemptMapper::toDomainModel);
    }

    @Override
    public List<ActivityAttempt> listarPorEstudianteYActividad(Long estudianteId, Long activityId) {
        return activityAttemptMapper.toDomainModelList(
                activityAttemptJpaRepository.findByEstudianteIdAndActivityIdOrderByAttemptNumberDesc(estudianteId, activityId));
    }

    @Override
    public int contarIntentosFinalizados(Long activityId, Long assignmentId, Long estudianteId) {
        return activityAttemptJpaRepository.contarPorEstados(activityId, assignmentId, estudianteId,
                List.of(EstadoActivityAttempt.COMPLETED, EstadoActivityAttempt.ABANDONED));
    }

    @Override
    public ActivityAttemptAnswer guardarRespuesta(ActivityAttemptAnswer answer) {
        ActivityAttemptAnswerEntity entity = activityAttemptMapper.toEntity(answer);
        return activityAttemptMapper.toDomainModel(activityAttemptAnswerJpaRepository.save(entity));
    }

    @Override
    public List<ActivityAttemptAnswer> listarRespuestas(Long attemptId) {
        return activityAttemptMapper.toAnswerDomainModelList(
                activityAttemptAnswerJpaRepository.findByAttemptId(attemptId));
    }

    @Override
    public Optional<ActivityAttemptAnswer> obtenerRespuesta(Long attemptId, Long contentItemId) {
        return activityAttemptAnswerJpaRepository.findByAttemptIdAndContentItemId(attemptId, contentItemId)
                .map(activityAttemptMapper::toDomainModel);
    }
}

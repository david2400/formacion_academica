package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_tematica.ActivityTematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.CrearActivityTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.ActivityTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.mappers.ActivityTematicaMapper;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity.ActivityTematicaEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.ActivityTematicaJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class ActivityTematicaJpaAdapter implements ActivityTematicaRepositoryPort {

    private final ActivityTematicaJpaRepository activityTematicaJpaRepository;
    private final ActivityTematicaMapper activityTematicaMapper;

    @Override
    public ActivityTematicaDto asignar(CrearActivityTematicaDto request) {
        if (activityTematicaJpaRepository.existsByActivityIdAndTematicaId(request.getActivityId(), request.getTematicaId())) {
            throw new IllegalArgumentException("La temática ya está asignada a esta actividad");
        }
        ActivityTematicaEntity asignacion = activityTematicaMapper.toEntity(request);
        return activityTematicaMapper.toDto(activityTematicaJpaRepository.save(asignacion));
    }

    @Override
    public List<ActivityTematicaDto> listarPorActivity(Long activityId) {
        return activityTematicaMapper.toDtoList(activityTematicaJpaRepository.findByActivityId(activityId));
    }

    @Override
    public void eliminar(Long activityId, Long tematicaId) {
        activityTematicaJpaRepository.findByActivityIdAndTematicaId(activityId, tematicaId)
                .ifPresentOrElse(activityTematicaJpaRepository::delete,
                        () -> { throw new IllegalArgumentException("La asignación no existe"); });
    }
}

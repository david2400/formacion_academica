package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.CrearActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.CrearActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearActivityService implements CrearActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;
    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;

    @Override
    public Activity crear(CrearActivityDto request) {
        activityTypeDefinitionRepositoryPort.obtenerPorId(request.getActivityTypeId())
                .orElseThrow(() -> new IllegalArgumentException("El tipo de actividad indicado no existe"));
        return activityRepositoryPort.guardar(request);
    }
}

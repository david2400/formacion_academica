package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ActualizarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.ActualizarActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarActivityService implements ActualizarActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public Activity actualizar(ActualizarActivityDto request) {
        return activityRepositoryPort.actualizar(request);
    }
}

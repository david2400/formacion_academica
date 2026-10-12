package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.DuplicarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuplicarActivityService implements DuplicarActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;
    private final ActivitySoporte activitySoporte;

    @Override
    public Activity duplicar(Long id) {
        activitySoporte.obtenerOFallar(id);
        return activityRepositoryPort.duplicar(id);
    }
}

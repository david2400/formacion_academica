package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ArchivarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ArchivarActivityService implements ArchivarActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public Activity archivar(Long id) {
        return activityRepositoryPort.archivar(id);
    }
}

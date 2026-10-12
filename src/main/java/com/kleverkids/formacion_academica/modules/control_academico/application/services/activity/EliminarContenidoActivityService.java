package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.EliminarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarContenidoActivityService implements EliminarContenidoActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public void eliminarContenido(Long activityId, Long contentItemId) {
        activityRepositoryPort.eliminarContenido(activityId, contentItemId);
    }
}

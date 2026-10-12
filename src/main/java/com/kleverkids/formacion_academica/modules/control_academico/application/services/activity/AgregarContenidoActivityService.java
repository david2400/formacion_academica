package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.AgregarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.AgregarContenidoActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AgregarContenidoActivityService implements AgregarContenidoActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;
    private final ActivitySoporte activitySoporte;

    @Override
    public ActivityContentItem agregarContenido(Long activityId, AgregarContenidoActivityDto request) {
        activitySoporte.obtenerOFallar(activityId);
        return activityRepositoryPort.agregarContenido(activityId, request);
    }
}

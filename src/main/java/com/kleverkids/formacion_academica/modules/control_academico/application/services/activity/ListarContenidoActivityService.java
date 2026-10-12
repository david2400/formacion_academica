package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ListarContenidoActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarContenidoActivityService implements ListarContenidoActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public List<ActivityContentItem> listarContenido(Long activityId) {
        return activityRepositoryPort.listarContenido(activityId);
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ListarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity.FiltroActivityDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarActivityService implements ListarActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public List<Activity> listarTodas() {
        return activityRepositoryPort.listarTodas();
    }

    @Override
    public List<Activity> buscar(FiltroActivityDto filtro) {
        if (filtro == null || filtro.estaVacio()) {
            return activityRepositoryPort.listarTodas();
        }
        return activityRepositoryPort.buscar(filtro);
    }
}

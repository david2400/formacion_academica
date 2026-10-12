package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.ConsultarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarActivityService implements ConsultarActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public Optional<Activity> consultarPorId(Long id) {
        return activityRepositoryPort.obtenerPorId(id);
    }
}

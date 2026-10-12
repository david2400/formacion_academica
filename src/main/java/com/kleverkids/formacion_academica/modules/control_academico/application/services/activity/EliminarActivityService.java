package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity.EliminarActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity.ActivityRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarActivityService implements EliminarActivityUseCase {

    private final ActivityRepositoryPort activityRepositoryPort;

    @Override
    public void eliminar(Long id) {
        activityRepositoryPort.eliminar(id);
    }
}

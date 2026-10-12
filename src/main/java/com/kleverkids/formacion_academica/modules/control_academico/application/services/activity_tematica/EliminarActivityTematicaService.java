package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica.EliminarActivityTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_tematica.ActivityTematicaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarActivityTematicaService implements EliminarActivityTematicaUseCase {

    private final ActivityTematicaRepositoryPort repositoryPort;

    @Override
    public void eliminar(Long activityId, Long tematicaId) {
        repositoryPort.eliminar(activityId, tematicaId);
    }
}

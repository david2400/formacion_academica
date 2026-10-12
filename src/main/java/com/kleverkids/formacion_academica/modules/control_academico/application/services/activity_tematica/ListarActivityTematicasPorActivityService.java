package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica.ListarActivityTematicasPorActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_tematica.ActivityTematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.ActivityTematicaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarActivityTematicasPorActivityService implements ListarActivityTematicasPorActivityUseCase {

    private final ActivityTematicaRepositoryPort repositoryPort;

    @Override
    public List<ActivityTematicaDto> listarPorActivity(Long activityId) {
        return repositoryPort.listarPorActivity(activityId);
    }
}

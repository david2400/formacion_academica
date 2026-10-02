package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica.CrearActivityTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica.EliminarActivityTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica.ListarActivityTematicasPorActivityUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_tematica.ActivityTematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.CrearActivityTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.ActivityTematicaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Mismo patrón que {@code ExamenTematicaService} (CRUD de la tabla puente
 * actividad&lt;-&gt;temática): el único valor agregado de este servicio es
 * delegar al puerto, la regla de "no duplicar la asignación" vive en el
 * adaptador (igual que en Examen).
 */
@RequiredArgsConstructor
@Service
public class ActivityTematicaService implements CrearActivityTematicaUseCase,
        ListarActivityTematicasPorActivityUseCase,
        EliminarActivityTematicaUseCase {

    private final ActivityTematicaRepositoryPort repositoryPort;

    @Override
    public ActivityTematicaDto asignar(CrearActivityTematicaDto request) {
        return repositoryPort.asignar(request);
    }

    @Override
    public List<ActivityTematicaDto> listarPorActivity(Long activityId) {
        return repositoryPort.listarPorActivity(activityId);
    }

    @Override
    public void eliminar(Long activityId, Long tematicaId) {
        repositoryPort.eliminar(activityId, tematicaId);
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_tematica.CrearActivityTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_tematica.ActivityTematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.ActivityTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica.CrearActivityTematicaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Asigna una temática a una actividad (tabla puente actividad&lt;-&gt;temática), igual
 * que en Examen: solo delega al puerto; la regla de "no duplicar la asignación"
 * vive en el adaptador.
 */
@Service
@RequiredArgsConstructor
public class CrearActivityTematicaService implements CrearActivityTematicaUseCase {

    private final ActivityTematicaRepositoryPort repositoryPort;

    @Override
    public ActivityTematicaDto asignar(CrearActivityTematicaDto request) {
        return repositoryPort.asignar(request);
    }
}

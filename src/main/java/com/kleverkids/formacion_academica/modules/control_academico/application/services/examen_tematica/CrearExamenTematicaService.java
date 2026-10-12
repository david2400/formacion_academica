package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica.CrearExamenTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_tematica.ExamenTematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.CrearExamenTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.ExamenTematicaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearExamenTematicaService implements CrearExamenTematicaUseCase {

    private final ExamenTematicaRepositoryPort repositoryPort;

    @Override
    public ExamenTematicaDto asignar(CrearExamenTematicaDto request) {
        return repositoryPort.asignar(request);
    }
}

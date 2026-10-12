package com.kleverkids.formacion_academica.modules.control_academico.application.services.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.CrearTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Tematica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearTematicaService implements CrearTematicaUseCase {

    private final TematicaRepositoryPort repositoryPort;

    @Override
    public Tematica crear(CrearTematicaDto request) {
        return repositoryPort.guardar(request);
    }
}

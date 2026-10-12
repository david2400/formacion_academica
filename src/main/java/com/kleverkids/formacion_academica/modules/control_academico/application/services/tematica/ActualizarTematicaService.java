package com.kleverkids.formacion_academica.modules.control_academico.application.services.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ActualizarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Tematica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarTematicaService implements ActualizarTematicaUseCase {

    private final TematicaRepositoryPort repositoryPort;

    @Override
    public Tematica actualizar(ActualizarTematicaDto request) {
        return repositoryPort.actualizar(request);
    }
}

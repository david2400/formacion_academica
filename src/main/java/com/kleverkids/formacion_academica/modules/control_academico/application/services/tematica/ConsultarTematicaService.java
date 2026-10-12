package com.kleverkids.formacion_academica.modules.control_academico.application.services.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ConsultarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Tematica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarTematicaService implements ConsultarTematicaUseCase {

    private final TematicaRepositoryPort repositoryPort;

    @Override
    public Tematica consultarPorId(Long tematicaId) {
        return repositoryPort.obtenerPorId(tematicaId);
    }
}

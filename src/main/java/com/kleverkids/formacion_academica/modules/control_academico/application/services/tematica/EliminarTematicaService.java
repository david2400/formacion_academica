package com.kleverkids.formacion_academica.modules.control_academico.application.services.tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.EliminarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarTematicaService implements EliminarTematicaUseCase {

    private final TematicaRepositoryPort repositoryPort;

    @Override
    public void eliminar(Long tematicaId) {
        repositoryPort.obtenerPorId(tematicaId);
        repositoryPort.eliminar(tematicaId);
    }
}

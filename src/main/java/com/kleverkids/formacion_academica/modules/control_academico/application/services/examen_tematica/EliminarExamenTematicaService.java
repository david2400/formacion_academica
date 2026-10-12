package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica.EliminarExamenTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_tematica.ExamenTematicaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarExamenTematicaService implements EliminarExamenTematicaUseCase {

    private final ExamenTematicaRepositoryPort repositoryPort;

    @Override
    public void eliminar(Long examenId, Long tematicaId) {
        repositoryPort.eliminar(examenId, tematicaId);
    }
}

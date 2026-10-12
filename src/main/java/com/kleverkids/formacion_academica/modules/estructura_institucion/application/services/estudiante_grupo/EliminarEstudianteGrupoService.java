package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.EliminarEstudianteGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarEstudianteGrupoService implements EliminarEstudianteGrupoUseCase {

    private final EstudianteGrupoRepositoryPort repositoryPort;

    @Override
    public void eliminar(Long estudianteGrupoId) {
        repositoryPort.eliminar(estudianteGrupoId);
    }
}

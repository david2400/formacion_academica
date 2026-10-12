package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ConsultarEstudianteGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarEstudianteGrupoService implements ConsultarEstudianteGrupoUseCase {

    private final EstudianteGrupoRepositoryPort repositoryPort;

    @Override
    public EstudianteGrupo consultarPorId(Long estudianteGrupoId) {
        return repositoryPort.consultarPorId(estudianteGrupoId);
    }
}

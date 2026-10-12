package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.CambiarEstadoEstudianteGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CambiarEstadoEstudianteGrupoService implements CambiarEstadoEstudianteGrupoUseCase {

    private final EstudianteGrupoRepositoryPort repositoryPort;

    @Override
    public EstudianteGrupo cambiarEstado(CambiarEstadoEstudianteGrupoDto request) {
        return repositoryPort.cambiarEstado(request);
    }
}

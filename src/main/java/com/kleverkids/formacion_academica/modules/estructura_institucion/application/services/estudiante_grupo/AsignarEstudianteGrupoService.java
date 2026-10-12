package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.AsignarEstudianteGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsignarEstudianteGrupoService implements AsignarEstudianteGrupoUseCase {

    private final EstudianteGrupoRepositoryPort repositoryPort;

    @Override
    public EstudianteGrupo asignar(AsignarEstudianteGrupoDto request) {
        return repositoryPort.asignar(request);
    }
}

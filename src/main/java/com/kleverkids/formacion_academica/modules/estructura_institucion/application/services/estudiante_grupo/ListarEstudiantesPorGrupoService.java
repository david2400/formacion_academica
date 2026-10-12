package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ListarEstudiantesPorGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarEstudiantesPorGrupoService implements ListarEstudiantesPorGrupoUseCase {

    private final EstudianteGrupoRepositoryPort repositoryPort;

    @Override
    public List<EstudianteGrupo> listar(Long grupoId) {
        return repositoryPort.listarPorGrupo(grupoId);
    }
}

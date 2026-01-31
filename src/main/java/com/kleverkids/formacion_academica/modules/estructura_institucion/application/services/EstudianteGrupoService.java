package com.kleverkids.formacion_academica.modules.estructura_institucion.application.services;

import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.AsignarEstudianteGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.CambiarEstadoEstudianteGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo.ListarEstudiantesPorGrupoUseCase;
import com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo.EstudianteGrupoRepositoryPort;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class EstudianteGrupoService implements AsignarEstudianteGrupoUseCase,
        CambiarEstadoEstudianteGrupoUseCase,
        ListarEstudiantesPorGrupoUseCase {

    private final EstudianteGrupoRepositoryPort repositoryPort;

    public EstudianteGrupoService(EstudianteGrupoRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public EstudianteGrupoDto asignar(AsignarEstudianteGrupoDto request) {
        return repositoryPort.asignar(request);
    }

    @Override
    public EstudianteGrupoDto cambiarEstado(CambiarEstadoEstudianteGrupoDto request) {
        return repositoryPort.cambiarEstado(request);
    }

    @Override
    public List<EstudianteGrupoDto> listar(UUID grupoId) {
        return repositoryPort.listarPorGrupo(grupoId);
    }
}

package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;

import java.util.List;

public interface EstudianteGrupoRepositoryPort {

    EstudianteGrupoDto asignar(AsignarEstudianteGrupoDto request);

    EstudianteGrupoDto cambiarEstado(CambiarEstadoEstudianteGrupoDto request);

    List<EstudianteGrupoDto> listarPorGrupo(Long grupoId);

    EstudianteGrupoDto consultarPorId(Long estudianteGrupoId);

    void eliminar(Long estudianteGrupoId);

    List<EstudianteGrupoDto> listar();
}

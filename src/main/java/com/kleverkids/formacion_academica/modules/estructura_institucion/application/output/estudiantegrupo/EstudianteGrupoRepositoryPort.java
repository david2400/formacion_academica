package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;

import java.util.List;

public interface EstudianteGrupoRepositoryPort {

    EstudianteGrupo asignar(AsignarEstudianteGrupoDto request);

    EstudianteGrupo cambiarEstado(CambiarEstadoEstudianteGrupoDto request);

    List<EstudianteGrupo> listarPorGrupo(Long grupoId);

    EstudianteGrupo consultarPorId(Long estudianteGrupoId);

    void eliminar(Long estudianteGrupoId);

    List<EstudianteGrupo> listar();
}

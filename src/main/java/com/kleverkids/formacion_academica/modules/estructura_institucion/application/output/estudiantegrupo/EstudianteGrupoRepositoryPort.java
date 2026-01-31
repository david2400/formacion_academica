package com.kleverkids.formacion_academica.modules.estructura_institucion.application.output.estudiantegrupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.AsignarEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;

import java.util.List;
import java.util.UUID;

public interface EstudianteGrupoRepositoryPort {

    EstudianteGrupoDto asignar(AsignarEstudianteGrupoDto request);

    EstudianteGrupoDto cambiarEstado(CambiarEstadoEstudianteGrupoDto request);

    List<EstudianteGrupoDto> listarPorGrupo(UUID grupoId);
}

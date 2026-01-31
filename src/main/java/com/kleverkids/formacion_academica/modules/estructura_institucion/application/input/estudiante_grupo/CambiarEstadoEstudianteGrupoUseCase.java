package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.CambiarEstadoEstudianteGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo.EstudianteGrupoDto;

public interface CambiarEstadoEstudianteGrupoUseCase {

    EstudianteGrupoDto cambiarEstado(CambiarEstadoEstudianteGrupoDto request);
}

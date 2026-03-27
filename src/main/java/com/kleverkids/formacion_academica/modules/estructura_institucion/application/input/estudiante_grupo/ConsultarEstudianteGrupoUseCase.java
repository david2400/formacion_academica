package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.estudiante_grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.EstudianteGrupo;

public interface ConsultarEstudianteGrupoUseCase {
    EstudianteGrupo consultarPorId(Long estudianteGrupoId);
}

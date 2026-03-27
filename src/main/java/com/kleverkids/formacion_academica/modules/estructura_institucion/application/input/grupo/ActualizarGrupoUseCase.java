package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;

public interface ActualizarGrupoUseCase {

    Grupo actualizar(ActualizarGrupoDto request);
}

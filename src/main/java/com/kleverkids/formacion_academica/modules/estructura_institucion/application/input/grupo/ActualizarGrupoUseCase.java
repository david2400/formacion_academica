package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.ActualizarGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.GrupoDto;

public interface ActualizarGrupoUseCase {

    GrupoDto actualizar(ActualizarGrupoDto request);
}

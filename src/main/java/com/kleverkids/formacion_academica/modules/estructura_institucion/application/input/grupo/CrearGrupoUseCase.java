package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grupo;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo.CrearGrupoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Grupo;

public interface CrearGrupoUseCase {

    Grupo crear(CrearGrupoDto request);
}

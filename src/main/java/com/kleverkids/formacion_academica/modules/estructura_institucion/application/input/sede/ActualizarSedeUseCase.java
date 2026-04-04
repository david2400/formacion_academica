package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.sede;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.sedes.ActualizarSedeDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model.Sede;

public interface ActualizarSedeUseCase {
    Sede actualizar(ActualizarSedeDto request);
}

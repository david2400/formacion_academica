package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.ActualizarGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;

public interface ActualizarGradoUseCase {

    GradoDto actualizar(ActualizarGradoDto request);
}

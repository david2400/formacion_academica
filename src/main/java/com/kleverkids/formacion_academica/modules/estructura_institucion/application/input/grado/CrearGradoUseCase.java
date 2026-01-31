package com.kleverkids.formacion_academica.modules.estructura_institucion.application.input.grado;

import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.CrearGradoDto;
import com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado.GradoDto;

public interface CrearGradoUseCase {

    GradoDto crear(CrearGradoDto request);
}

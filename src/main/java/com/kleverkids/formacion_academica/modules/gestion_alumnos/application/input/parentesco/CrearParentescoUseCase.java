package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.CrearParentescoDto;

public interface CrearParentescoUseCase {
    Parentesco crear(CrearParentescoDto request);
}

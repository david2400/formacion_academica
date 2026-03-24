package com.kleverkids.formacion_academica.modules.gestion_alumnos.application.input.parentesco;

import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Parentesco;
import com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.parentesco.ActualizarParentescoDto;

public interface ActualizarParentescoUseCase {
    Parentesco actualizar(ActualizarParentescoDto request);
}

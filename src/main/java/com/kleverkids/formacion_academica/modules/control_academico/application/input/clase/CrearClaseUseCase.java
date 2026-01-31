package com.kleverkids.formacion_academica.modules.control_academico.application.input.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClaseDto;

public interface CrearClaseUseCase {

    ClaseDto crearClase(CrearClaseDto request);
}

package com.kleverkids.formacion_academica.modules.control_academico.application.input.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ActualizarClaseDto;

public interface ActualizarClaseUseCase {

    Clase actualizar(ActualizarClaseDto request);
}

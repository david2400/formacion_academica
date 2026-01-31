package com.kleverkids.formacion_academica.modules.control_academico.application.input.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.CrearClasesMasivasDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.ResultadoClasesMasivasDto;

public interface CrearClasesMasivasUseCase {

    ResultadoClasesMasivasDto crearClases(CrearClasesMasivasDto request);
}

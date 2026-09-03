package com.kleverkids.formacion_academica.modules.control_academico.application.input.clase;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase.RegistrarSeguimientoClaseDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Clase;

public interface RegistrarSeguimientoClaseUseCase {

    Clase registrarSeguimiento(Long id, RegistrarSeguimientoClaseDto request);
}

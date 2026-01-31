package com.kleverkids.formacion_academica.modules.control_academico.application.input.intento;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.FinalizarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;

public interface FinalizarIntentoExamenUseCase {

    IntentoExamenDto finalizar(FinalizarIntentoExamenDto request);
}

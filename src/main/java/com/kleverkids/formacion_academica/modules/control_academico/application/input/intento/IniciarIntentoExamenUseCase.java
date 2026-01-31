package com.kleverkids.formacion_academica.modules.control_academico.application.input.intento;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IniciarIntentoExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.IntentoExamenDto;

public interface IniciarIntentoExamenUseCase {

    IntentoExamenDto iniciar(IniciarIntentoExamenDto request);
}

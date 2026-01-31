package com.kleverkids.formacion_academica.modules.control_academico.application.input.intento;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RegistrarRespuestaIntentoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento.RespuestaIntentoDto;

public interface RegistrarRespuestaIntentoUseCase {

    RespuestaIntentoDto registrar(RegistrarRespuestaIntentoDto request);
}

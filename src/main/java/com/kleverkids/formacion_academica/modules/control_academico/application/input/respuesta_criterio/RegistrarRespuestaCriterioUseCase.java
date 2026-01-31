package com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio.RegistrarRespuestaCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio.RespuestaCriterioDto;

public interface RegistrarRespuestaCriterioUseCase {

    RespuestaCriterioDto registrar(RegistrarRespuestaCriterioDto request);
}

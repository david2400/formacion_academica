package com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;



public interface ConsultarObservacionCriterioUseCase {

    ObservacionCriterioDto consultarPorId(Long observacionId);
}

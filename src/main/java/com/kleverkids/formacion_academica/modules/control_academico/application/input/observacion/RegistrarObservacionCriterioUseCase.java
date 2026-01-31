package com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;

public interface RegistrarObservacionCriterioUseCase {

    ObservacionCriterioDto registrar(RegistrarObservacionCriterioDto request);
}

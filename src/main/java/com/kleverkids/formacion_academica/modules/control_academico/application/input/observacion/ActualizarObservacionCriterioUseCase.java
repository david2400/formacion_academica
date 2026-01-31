package com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;

public interface ActualizarObservacionCriterioUseCase {

    ObservacionCriterioDto actualizar(ActualizarObservacionCriterioDto request);
}

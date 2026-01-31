package com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;

public interface ActualizarCriterioExamenUseCase {

    CriterioExamenDto actualizar(ActualizarCriterioExamenDto request);
}

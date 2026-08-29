package com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.ActualizarCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CriterioEvaluadosDto;

public interface ActualizarCriterioEvaluadosUseCase {

    CriterioEvaluadosDto actualizar(ActualizarCriterioEvaluadosDto request);
}

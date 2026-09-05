package com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CrearCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.CriterioEvaluados;

public interface CrearCriterioEvaluadosUseCase {

    CriterioEvaluados crear(CrearCriterioEvaluadosDto request);
}

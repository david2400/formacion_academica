package com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.CriterioEvaluados;

public interface ConsultarCriterioEvaluadosUseCase {

    CriterioEvaluados consultarPorId(Long criterioId);
}

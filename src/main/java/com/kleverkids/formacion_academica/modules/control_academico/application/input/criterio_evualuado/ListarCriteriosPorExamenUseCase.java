package com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.CriterioEvaluados;

import java.util.List;

public interface ListarCriteriosPorExamenUseCase {

    List<CriterioEvaluados> listar(Long examenId);
}

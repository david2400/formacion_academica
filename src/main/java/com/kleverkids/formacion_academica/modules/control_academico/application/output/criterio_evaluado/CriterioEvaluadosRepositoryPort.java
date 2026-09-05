package com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio_evaluado;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.ActualizarCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CrearCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.CriterioEvaluados;

import java.util.List;

public interface CriterioEvaluadosRepositoryPort {

    CriterioEvaluados guardar(CrearCriterioEvaluadosDto request);

    CriterioEvaluados actualizar(ActualizarCriterioEvaluadosDto request);

    List<CriterioEvaluados> listarPorExamen(Long examenId);

    CriterioEvaluados obtenerPorId(Long criterioId);

    void eliminar(Long criterioId);
}

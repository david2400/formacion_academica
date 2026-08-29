package com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio_evaluado;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.ActualizarCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CrearCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CriterioEvaluadosDto;

import java.util.List;

public interface CriterioEvaluadosRepositoryPort {

    CriterioEvaluadosDto guardar(CrearCriterioEvaluadosDto request);

    CriterioEvaluadosDto actualizar(ActualizarCriterioEvaluadosDto request);

    List<CriterioEvaluadosDto> listarPorExamen(Long examenId);

    CriterioEvaluadosDto obtenerPorId(Long criterioId);

    void eliminar(Long criterioId);
}

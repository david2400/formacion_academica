package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ActualizarExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.CrearExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ExamenCriterioDto;

import java.util.List;

public interface ExamenCriterioRepositoryPort {

    ExamenCriterioDto guardar(CrearExamenCriterioDto request);

    ExamenCriterioDto actualizar(ActualizarExamenCriterioDto request);

    List<ExamenCriterioDto> listarPorExamen(Long examenId);

    ExamenCriterioDto obtenerPorId(Long id);

    void eliminar(Long id);
}

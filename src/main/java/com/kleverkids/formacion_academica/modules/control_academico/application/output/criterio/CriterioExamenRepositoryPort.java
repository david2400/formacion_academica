package com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;

import java.util.List;
import java.util.UUID;

public interface CriterioExamenRepositoryPort {

    CriterioExamenDto guardar(CrearCriterioExamenDto request);

    CriterioExamenDto actualizar(ActualizarCriterioExamenDto request);

    List<CriterioExamenDto> listarPorExamen(UUID examenId);

    CriterioExamenDto obtenerPorId(UUID criterioId);

    void eliminar(UUID criterioId);
}

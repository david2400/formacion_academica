package com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;

public interface CrearCriterioExamenUseCase {

    CriterioExamenDto crear(CrearCriterioExamenDto request);
}

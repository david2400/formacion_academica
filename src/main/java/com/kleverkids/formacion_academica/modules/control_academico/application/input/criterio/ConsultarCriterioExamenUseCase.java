package com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;

public interface ConsultarCriterioExamenUseCase {

    CriterioExamenDto consultarPorId(Long criterioId);
}

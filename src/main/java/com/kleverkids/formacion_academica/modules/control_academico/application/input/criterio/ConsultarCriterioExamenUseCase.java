package com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;

import java.util.UUID;

public interface ConsultarCriterioExamenUseCase {

    CriterioExamenDto consultarPorId(UUID criterioId);
}

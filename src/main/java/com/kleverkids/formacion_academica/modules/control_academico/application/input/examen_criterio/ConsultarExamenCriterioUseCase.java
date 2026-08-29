package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ExamenCriterioDto;

public interface ConsultarExamenCriterioUseCase {

    ExamenCriterioDto consultarPorId(Long id);
}

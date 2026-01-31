package com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;

import java.util.List;
import java.util.UUID;

public interface ListarCriteriosPorExamenUseCase {

    List<CriterioExamenDto> listar(UUID examenId);
}

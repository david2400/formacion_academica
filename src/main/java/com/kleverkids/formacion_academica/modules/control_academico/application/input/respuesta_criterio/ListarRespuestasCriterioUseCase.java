package com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio.RespuestaCriterioDto;

import java.util.List;
import java.util.UUID;

public interface ListarRespuestasCriterioUseCase {

    List<RespuestaCriterioDto> listar(UUID examenId, UUID estudianteId);
}

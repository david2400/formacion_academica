package com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio.RespuestaCriterioDto;

import java.util.List;


public interface ListarRespuestasCriterioUseCase {

    List<RespuestaCriterioDto> listar(Long examenId, Long estudianteId);
}

package com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;

import java.util.List;
import java.util.UUID;

public interface ListarRespuestasPreguntaUseCase {

    List<RespuestaPreguntaDto> listar(UUID examenId, UUID estudianteId);
}

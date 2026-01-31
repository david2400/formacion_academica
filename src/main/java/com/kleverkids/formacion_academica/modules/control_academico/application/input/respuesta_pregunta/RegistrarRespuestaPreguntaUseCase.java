package com.kleverkids.formacion_academica.modules.control_academico.application.input.respuesta_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RegistrarRespuestaPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_pregunta.RespuestaPreguntaDto;

public interface RegistrarRespuestaPreguntaUseCase {

    RespuestaPreguntaDto registrar(RegistrarRespuestaPreguntaDto request);
}

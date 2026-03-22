package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.RespuestaPregunta;

public interface CrearPreguntaUseCase {
    
    RespuestaPregunta create(CreateQuestionCommand command);
}

package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.UpdateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.RespuestaPregunta;

public interface ActualizarPreguntaUseCase {
    
    RespuestaPregunta actualizar(Long id, UpdateQuestionCommand command);
}

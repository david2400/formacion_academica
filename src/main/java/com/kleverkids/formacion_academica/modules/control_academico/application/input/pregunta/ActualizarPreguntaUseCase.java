package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.QuestionResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.UpdateQuestionCommand;

public interface ActualizarPreguntaUseCase {
    
    QuestionResponse actualizar(Long id, UpdateQuestionCommand command);
}

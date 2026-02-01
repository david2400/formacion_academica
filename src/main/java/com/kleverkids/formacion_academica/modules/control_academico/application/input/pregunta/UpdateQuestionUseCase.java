package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.QuestionResponse;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.UpdateQuestionCommand;

import java.util.UUID;

public interface UpdateQuestionUseCase {
    
    QuestionResponse update(UUID id, UpdateQuestionCommand command);
}

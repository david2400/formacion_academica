package com.kleverkids.formacion_academica.modules.questions.application.port.in;

import com.kleverkids.formacion_academica.modules.questions.application.dto.QuestionResponse;
import com.kleverkids.formacion_academica.modules.questions.application.dto.UpdateQuestionCommand;

import java.util.UUID;

public interface UpdateQuestionUseCase {
    
    QuestionResponse update(UUID id, UpdateQuestionCommand command);
}

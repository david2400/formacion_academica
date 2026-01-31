package com.kleverkids.formacion_academica.modules.questions.application.port.in;

import com.kleverkids.formacion_academica.modules.questions.application.dto.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.questions.application.dto.QuestionResponse;

public interface CreateQuestionUseCase {
    
    QuestionResponse create(CreateQuestionCommand command);
}

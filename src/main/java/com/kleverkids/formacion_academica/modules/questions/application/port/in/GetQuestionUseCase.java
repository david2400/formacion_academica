package com.kleverkids.formacion_academica.modules.questions.application.port.in;

import com.kleverkids.formacion_academica.modules.questions.application.dto.QuestionResponse;

import java.util.UUID;

public interface GetQuestionUseCase {
    
    QuestionResponse getById(UUID id);
}

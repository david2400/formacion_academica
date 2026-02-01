package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.QuestionResponse;

import java.util.UUID;

public interface GetQuestionUseCase {
    
    QuestionResponse getById(UUID id);
}

package com.kleverkids.formacion_academica.modules.questions.application.port.in;

import com.kleverkids.formacion_academica.modules.questions.application.dto.AnswerValidationRequest;
import com.kleverkids.formacion_academica.modules.questions.application.dto.ValidationResult;

import java.util.UUID;

public interface ValidateAnswerUseCase {
    
    ValidationResult validate(UUID questionId, AnswerValidationRequest request);
}

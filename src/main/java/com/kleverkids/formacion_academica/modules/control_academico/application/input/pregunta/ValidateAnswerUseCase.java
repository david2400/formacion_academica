package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.AnswerValidationRequest;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ValidationResult;

import java.util.UUID;

public interface ValidateAnswerUseCase {
    
    ValidationResult validate(UUID questionId, AnswerValidationRequest request);
}

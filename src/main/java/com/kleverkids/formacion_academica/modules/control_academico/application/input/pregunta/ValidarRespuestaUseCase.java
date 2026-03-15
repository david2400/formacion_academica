package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.AnswerValidationRequest;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ValidationResult;

public interface ValidarRespuestaUseCase {
    
    ValidationResult validar(Long preguntaId, AnswerValidationRequest request);
}

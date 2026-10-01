package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.RegistrarRespuestaAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;

public interface RegistrarRespuestaAttemptUseCase {

    ActivityAttemptAnswer registrarRespuesta(RegistrarRespuestaAttemptDto request);
}

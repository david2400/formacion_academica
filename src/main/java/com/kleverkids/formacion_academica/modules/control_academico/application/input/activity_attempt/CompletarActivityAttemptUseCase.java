package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;

public interface CompletarActivityAttemptUseCase {

    ActivityAttempt completar(Long attemptId);
}

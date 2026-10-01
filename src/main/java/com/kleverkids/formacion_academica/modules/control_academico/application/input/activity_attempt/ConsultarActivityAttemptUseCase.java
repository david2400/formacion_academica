package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;

import java.util.List;

public interface ConsultarActivityAttemptUseCase {

    ActivityAttempt consultarPorId(Long id);

    List<ActivityAttemptAnswer> listarRespuestas(Long attemptId);
}

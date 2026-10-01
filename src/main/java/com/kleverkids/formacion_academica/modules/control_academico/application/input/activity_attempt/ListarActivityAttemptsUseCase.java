package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;

import java.util.List;

public interface ListarActivityAttemptsUseCase {

    List<ActivityAttempt> listarPorEstudianteYActividad(Long estudianteId, Long activityId);
}

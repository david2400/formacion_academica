package com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;

import java.util.List;
import java.util.Optional;

public interface ActivityAttemptRepositoryPort {

    ActivityAttempt guardar(ActivityAttempt attempt);

    Optional<ActivityAttempt> obtenerPorId(Long id);

    /** Intento IN_PROGRESS existente para esa combinación, si lo hay (para resumir en vez de duplicar). */
    Optional<ActivityAttempt> obtenerEnProgreso(Long activityId, Long assignmentId, Long estudianteId);

    List<ActivityAttempt> listarPorEstudianteYActividad(Long estudianteId, Long activityId);

    int contarIntentosFinalizados(Long activityId, Long assignmentId, Long estudianteId);

    ActivityAttemptAnswer guardarRespuesta(ActivityAttemptAnswer answer);

    List<ActivityAttemptAnswer> listarRespuestas(Long attemptId);

    Optional<ActivityAttemptAnswer> obtenerRespuesta(Long attemptId, Long contentItemId);
}

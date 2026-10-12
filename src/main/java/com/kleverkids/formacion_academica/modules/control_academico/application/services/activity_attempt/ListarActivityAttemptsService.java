package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.ListarActivityAttemptsUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarActivityAttemptsService implements ListarActivityAttemptsUseCase {

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;

    @Override
    public List<ActivityAttempt> listarPorEstudianteYActividad(Long estudianteId, Long activityId) {
        return activityAttemptRepositoryPort.listarPorEstudianteYActividad(estudianteId, activityId);
    }
}

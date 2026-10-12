package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_attempt;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_attempt.ConsultarActivityAttemptUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt.ActivityAttemptRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttempt;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultarActivityAttemptService implements ConsultarActivityAttemptUseCase {

    private final ActivityAttemptRepositoryPort activityAttemptRepositoryPort;

    @Override
    public ActivityAttempt consultarPorId(Long id) {
        return activityAttemptRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Intento no encontrado"));
    }

    @Override
    public List<ActivityAttemptAnswer> listarRespuestas(Long attemptId) {
        return activityAttemptRepositoryPort.listarRespuestas(attemptId);
    }
}

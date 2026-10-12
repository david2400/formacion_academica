package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.ActualizarExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_criterio.ExamenCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ActualizarExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ExamenCriterioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarExamenCriterioService implements ActualizarExamenCriterioUseCase {

    private final ExamenCriterioRepositoryPort repositoryPort;

    @Override
    public ExamenCriterioDto actualizar(ActualizarExamenCriterioDto request) {
        return repositoryPort.actualizar(request);
    }
}

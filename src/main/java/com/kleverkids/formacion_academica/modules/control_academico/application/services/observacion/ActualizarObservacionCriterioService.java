package com.kleverkids.formacion_academica.modules.control_academico.application.services.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ActualizarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion.ObservacionCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarObservacionCriterioService implements ActualizarObservacionCriterioUseCase {

    private final ObservacionCriterioRepositoryPort repositoryPort;

    @Override
    public ObservacionCriterioDto actualizar(ActualizarObservacionCriterioDto request) {
        return repositoryPort.actualizar(request);
    }
}

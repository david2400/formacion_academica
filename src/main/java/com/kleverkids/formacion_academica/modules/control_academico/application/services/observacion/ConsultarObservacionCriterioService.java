package com.kleverkids.formacion_academica.modules.control_academico.application.services.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ConsultarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion.ObservacionCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarObservacionCriterioService implements ConsultarObservacionCriterioUseCase {

    private final ObservacionCriterioRepositoryPort repositoryPort;

    @Override
    public ObservacionCriterioDto consultarPorId(Long observacionId) {
        return repositoryPort.obtenerPorId(observacionId);
    }
}

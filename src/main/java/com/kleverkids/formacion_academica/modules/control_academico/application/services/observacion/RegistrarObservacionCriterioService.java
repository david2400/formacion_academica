package com.kleverkids.formacion_academica.modules.control_academico.application.services.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.RegistrarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion.ObservacionCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrarObservacionCriterioService implements RegistrarObservacionCriterioUseCase {

    private final ObservacionCriterioRepositoryPort repositoryPort;

    @Override
    public ObservacionCriterioDto registrar(RegistrarObservacionCriterioDto request) {
        return repositoryPort.registrar(request);
    }
}

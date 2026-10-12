package com.kleverkids.formacion_academica.modules.control_academico.application.services.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.EliminarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion.ObservacionCriterioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarObservacionCriterioService implements EliminarObservacionCriterioUseCase {

    private final ObservacionCriterioRepositoryPort repositoryPort;

    @Override
    public void eliminar(Long observacionId) {
        repositoryPort.obtenerPorId(observacionId);
        repositoryPort.eliminar(observacionId);
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services.criterio_evualuado;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado.EliminarCriterioEvaluadosUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio_evaluado.CriterioEvaluadosRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarCriterioEvaluadosService implements EliminarCriterioEvaluadosUseCase {

    private final CriterioEvaluadosRepositoryPort repositoryPort;

    @Override
    public void eliminar(Long criterioId) {
        repositoryPort.obtenerPorId(criterioId);
        repositoryPort.eliminar(criterioId);
    }
}

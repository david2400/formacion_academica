package com.kleverkids.formacion_academica.modules.control_academico.application.services.criterio_evualuado;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado.ConsultarCriterioEvaluadosUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio_evaluado.CriterioEvaluadosRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.CriterioEvaluados;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarCriterioEvaluadosService implements ConsultarCriterioEvaluadosUseCase {

    private final CriterioEvaluadosRepositoryPort repositoryPort;

    @Override
    public CriterioEvaluados consultarPorId(Long criterioId) {
        return repositoryPort.obtenerPorId(criterioId);
    }
}

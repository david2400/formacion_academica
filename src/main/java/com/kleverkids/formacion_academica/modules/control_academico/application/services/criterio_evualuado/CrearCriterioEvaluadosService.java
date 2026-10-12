package com.kleverkids.formacion_academica.modules.control_academico.application.services.criterio_evualuado;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado.CrearCriterioEvaluadosUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio_evaluado.CriterioEvaluadosRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CrearCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.CriterioEvaluados;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearCriterioEvaluadosService implements CrearCriterioEvaluadosUseCase {

    private final CriterioEvaluadosRepositoryPort repositoryPort;

    @Override
    public CriterioEvaluados crear(CrearCriterioEvaluadosDto request) {
        return repositoryPort.guardar(request);
    }
}

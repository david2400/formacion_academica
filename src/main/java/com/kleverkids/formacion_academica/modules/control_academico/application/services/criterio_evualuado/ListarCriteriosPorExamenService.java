package com.kleverkids.formacion_academica.modules.control_academico.application.services.criterio_evualuado;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado.ListarCriteriosPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio_evaluado.CriterioEvaluadosRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.CriterioEvaluados;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarCriteriosPorExamenService implements ListarCriteriosPorExamenUseCase {

    private final CriterioEvaluadosRepositoryPort repositoryPort;

    @Override
    public List<CriterioEvaluados> listar(Long examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }
}

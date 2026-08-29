package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado.ActualizarCriterioEvaluadosUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado.ConsultarCriterioEvaluadosUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado.CrearCriterioEvaluadosUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado.EliminarCriterioEvaluadosUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio_evualuado.ListarCriteriosPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio_evaluado.CriterioEvaluadosRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.ActualizarCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CrearCriterioEvaluadosDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado.CriterioEvaluadosDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class CriterioEvaluadosService implements CrearCriterioEvaluadosUseCase,
        ActualizarCriterioEvaluadosUseCase,
        ListarCriteriosPorExamenUseCase,
        ConsultarCriterioEvaluadosUseCase,
        EliminarCriterioEvaluadosUseCase {

    private final CriterioEvaluadosRepositoryPort repositoryPort;

    @Override
    public CriterioEvaluadosDto crear(CrearCriterioEvaluadosDto request) {
        return repositoryPort.guardar(request);
    }

    @Override
    public CriterioEvaluadosDto actualizar(ActualizarCriterioEvaluadosDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public List<CriterioEvaluadosDto> listar(Long examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }

    @Override
    public CriterioEvaluadosDto consultarPorId(Long criterioId) {
        return repositoryPort.obtenerPorId(criterioId);
    }

    @Override
    public void eliminar(Long criterioId) {
        repositoryPort.obtenerPorId(criterioId);
        repositoryPort.eliminar(criterioId);
    }
}

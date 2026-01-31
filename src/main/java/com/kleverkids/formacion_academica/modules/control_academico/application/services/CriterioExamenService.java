package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ActualizarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.CrearCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ListarCriteriosPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio.CriterioExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CriterioExamenService implements CrearCriterioExamenUseCase,
        ActualizarCriterioExamenUseCase,
        ListarCriteriosPorExamenUseCase {

    private final CriterioExamenRepositoryPort repositoryPort;

    public CriterioExamenService(CriterioExamenRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public CriterioExamenDto crear(CrearCriterioExamenDto request) {
        return repositoryPort.guardar(request);
    }

    @Override
    public CriterioExamenDto actualizar(ActualizarCriterioExamenDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public List<CriterioExamenDto> listar(UUID examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ActualizarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ConsultarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.CrearCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.EliminarCriterioExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.criterio.ListarCriteriosPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.criterio.CriterioExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.ActualizarCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CrearCriterioExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio.CriterioExamenDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CriterioExamenService implements CrearCriterioExamenUseCase,
        ActualizarCriterioExamenUseCase,
        ListarCriteriosPorExamenUseCase,
        ConsultarCriterioExamenUseCase,
        EliminarCriterioExamenUseCase {

    private final CriterioExamenRepositoryPort repositoryPort;

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

    @Override
    public CriterioExamenDto consultarPorId(UUID criterioId) {
        return repositoryPort.obtenerPorId(criterioId);
    }

    @Override
    public void eliminar(UUID criterioId) {
        repositoryPort.obtenerPorId(criterioId);
        repositoryPort.eliminar(criterioId);
    }
}

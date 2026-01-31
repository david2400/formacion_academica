package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ActualizarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ListarObservacionesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.RegistrarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion.ObservacionCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class ObservacionCriterioService implements RegistrarObservacionCriterioUseCase,
        ActualizarObservacionCriterioUseCase,
        ListarObservacionesPorEstudianteUseCase {

    private final ObservacionCriterioRepositoryPort repositoryPort;

    public ObservacionCriterioService(ObservacionCriterioRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public ObservacionCriterioDto registrar(RegistrarObservacionCriterioDto request) {
        return repositoryPort.registrar(request);
    }

    @Override
    public ObservacionCriterioDto actualizar(ActualizarObservacionCriterioDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public List<ObservacionCriterioDto> listar(UUID examenId, UUID estudianteId) {
        return repositoryPort.listarPorEstudiante(examenId, estudianteId);
    }
}

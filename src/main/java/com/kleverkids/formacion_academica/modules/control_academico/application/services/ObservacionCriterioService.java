package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ActualizarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ConsultarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.EliminarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ListarObservacionesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.RegistrarObservacionCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion.ObservacionCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ActualizarObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.RegistrarObservacionCriterioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ObservacionCriterioService implements RegistrarObservacionCriterioUseCase,
        ActualizarObservacionCriterioUseCase,
        ListarObservacionesPorEstudianteUseCase,
        ConsultarObservacionCriterioUseCase,
        EliminarObservacionCriterioUseCase {

    private final ObservacionCriterioRepositoryPort repositoryPort;


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

    @Override
    public ObservacionCriterioDto consultarPorId(UUID observacionId) {
        return repositoryPort.obtenerPorId(observacionId);
    }

    @Override
    public void eliminar(UUID observacionId) {
        repositoryPort.obtenerPorId(observacionId);
        repositoryPort.eliminar(observacionId);
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.ActualizarExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.ConsultarExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.CrearExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.EliminarExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.ListarExamenCriterioPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_criterio.ExamenCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ActualizarExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.CrearExamenCriterioDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ExamenCriterioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamenCriterioService implements CrearExamenCriterioUseCase,
        ActualizarExamenCriterioUseCase,
        ListarExamenCriterioPorExamenUseCase,
        ConsultarExamenCriterioUseCase,
        EliminarExamenCriterioUseCase {

    private final ExamenCriterioRepositoryPort repositoryPort;

    @Override
    public ExamenCriterioDto crear(CrearExamenCriterioDto request) {
        return repositoryPort.guardar(request);
    }

    @Override
    public ExamenCriterioDto actualizar(ActualizarExamenCriterioDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public List<ExamenCriterioDto> listarPorExamen(Long examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }

    @Override
    public ExamenCriterioDto consultarPorId(Long id) {
        return repositoryPort.obtenerPorId(id);
    }

    @Override
    public void eliminar(Long id) {
        repositoryPort.obtenerPorId(id);
        repositoryPort.eliminar(id);
    }
}

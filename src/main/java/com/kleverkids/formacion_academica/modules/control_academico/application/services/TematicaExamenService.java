package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ActualizarTematicaExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.CrearTematicaExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ListarTematicasPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaExamenRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaExamenDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaExamenDto;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TematicaExamenService implements CrearTematicaExamenUseCase,
        ActualizarTematicaExamenUseCase,
        ListarTematicasPorExamenUseCase {

    private final TematicaExamenRepositoryPort repositoryPort;

    public TematicaExamenService(TematicaExamenRepositoryPort repositoryPort) {
        this.repositoryPort = repositoryPort;
    }

    @Override
    public TematicaExamenDto crear(CrearTematicaExamenDto request) {
        return repositoryPort.guardar(request);
    }

    @Override
    public TematicaExamenDto actualizar(ActualizarTematicaExamenDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public List<TematicaExamenDto> listar(UUID examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }
}

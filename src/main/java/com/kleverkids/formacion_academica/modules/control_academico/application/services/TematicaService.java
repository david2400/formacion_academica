package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ActualizarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ConsultarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.CrearTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.EliminarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ListarTematicasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.TematicaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class TematicaService implements CrearTematicaUseCase,
        ActualizarTematicaUseCase,
        ListarTematicasUseCase,
        ConsultarTematicaUseCase,
        EliminarTematicaUseCase {

    private final TematicaRepositoryPort repositoryPort;

    @Override
    public TematicaDto crear(CrearTematicaDto request) {
        return repositoryPort.guardar(request);
    }

    @Override
    public TematicaDto actualizar(ActualizarTematicaDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public List<TematicaDto> listar(UUID examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }

    @Override
    public TematicaDto consultarPorId(UUID tematicaId) {
        return repositoryPort.obtenerPorId(tematicaId);
    }

    @Override
    public void eliminar(UUID tematicaId) {
        repositoryPort.obtenerPorId(tematicaId);
        repositoryPort.eliminar(tematicaId);
    }
}

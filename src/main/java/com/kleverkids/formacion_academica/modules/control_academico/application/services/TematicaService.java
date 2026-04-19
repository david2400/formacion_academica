package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ActualizarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ConsultarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.CrearTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.EliminarTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.tematica.ListarTematicasUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.tematica.TematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.ActualizarTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica.CrearTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Tematica;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class TematicaService implements CrearTematicaUseCase,
        ActualizarTematicaUseCase,
        ListarTematicasUseCase,
        ConsultarTematicaUseCase,
        EliminarTematicaUseCase {

    private final TematicaRepositoryPort repositoryPort;

    @Override
    public Tematica crear(CrearTematicaDto request) {
        return repositoryPort.guardar(request);
    }

    @Override
    public Tematica actualizar(ActualizarTematicaDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public List<Tematica> listar() {
        return repositoryPort.listar();
    }

    @Override
    public Tematica consultarPorId(Long tematicaId) {
        return repositoryPort.obtenerPorId(tematicaId);
    }

    @Override
    public void eliminar(Long tematicaId) {
        repositoryPort.obtenerPorId(tematicaId);
        repositoryPort.eliminar(tematicaId);
    }
}

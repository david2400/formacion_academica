package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica.CrearExamenTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica.EliminarExamenTematicaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica.ListarExamenTematicasPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_tematica.ExamenTematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.CrearExamenTematicaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.ExamenTematicaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamenTematicaService implements CrearExamenTematicaUseCase,
        ListarExamenTematicasPorExamenUseCase,
        EliminarExamenTematicaUseCase {

    private final ExamenTematicaRepositoryPort repositoryPort;

    @Override
    public ExamenTematicaDto asignar(CrearExamenTematicaDto request) {
        return repositoryPort.asignar(request);
    }

    @Override
    public List<ExamenTematicaDto> listarPorExamen(Long examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }

    @Override
    public void eliminar(Long examenId, Long tematicaId) {
        repositoryPort.eliminar(examenId, tematicaId);
    }
}

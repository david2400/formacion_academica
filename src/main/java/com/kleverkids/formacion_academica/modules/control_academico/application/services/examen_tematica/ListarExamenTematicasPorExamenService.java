package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_tematica;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_tematica.ListarExamenTematicasPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_tematica.ExamenTematicaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica.ExamenTematicaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarExamenTematicasPorExamenService implements ListarExamenTematicasPorExamenUseCase {

    private final ExamenTematicaRepositoryPort repositoryPort;

    @Override
    public List<ExamenTematicaDto> listarPorExamen(Long examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }
}

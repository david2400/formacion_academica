package com.kleverkids.formacion_academica.modules.control_academico.application.services.observacion;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.observacion.ListarObservacionesPorEstudianteUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.observacion.ObservacionCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion.ObservacionCriterioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarObservacionesPorEstudianteService implements ListarObservacionesPorEstudianteUseCase {

    private final ObservacionCriterioRepositoryPort repositoryPort;

    @Override
    public List<ObservacionCriterioDto> listar(Long examenId, Long estudianteId) {
        return repositoryPort.listarPorEstudiante(examenId, estudianteId);
    }
}

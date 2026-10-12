package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.ListarExamenCriterioPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_criterio.ExamenCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ExamenCriterioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarExamenCriterioPorExamenService implements ListarExamenCriterioPorExamenUseCase {

    private final ExamenCriterioRepositoryPort repositoryPort;

    @Override
    public List<ExamenCriterioDto> listarPorExamen(Long examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.ConsultarExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_criterio.ExamenCriterioRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio.ExamenCriterioDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarExamenCriterioService implements ConsultarExamenCriterioUseCase {

    private final ExamenCriterioRepositoryPort repositoryPort;

    @Override
    public ExamenCriterioDto consultarPorId(Long id) {
        return repositoryPort.obtenerPorId(id);
    }
}

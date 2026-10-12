package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_criterio;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_criterio.EliminarExamenCriterioUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_criterio.ExamenCriterioRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarExamenCriterioService implements EliminarExamenCriterioUseCase {

    private final ExamenCriterioRepositoryPort repositoryPort;

    @Override
    public void eliminar(Long id) {
        repositoryPort.obtenerPorId(id);
        repositoryPort.eliminar(id);
    }
}

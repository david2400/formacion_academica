package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.EliminarExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_pregunta.ExamenPreguntaRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarExamenPreguntaService implements EliminarExamenPreguntaUseCase {

    private final ExamenPreguntaRepositoryPort repositoryPort;

    @Override
    public void eliminar(Long id) {
        repositoryPort.obtenerPorId(id);
        repositoryPort.eliminar(id);
    }
}

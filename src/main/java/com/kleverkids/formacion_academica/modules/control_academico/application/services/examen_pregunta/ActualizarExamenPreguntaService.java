package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.ActualizarExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_pregunta.ExamenPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ActualizarExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarExamenPreguntaService implements ActualizarExamenPreguntaUseCase {

    private final ExamenPreguntaRepositoryPort repositoryPort;

    @Override
    public ExamenPreguntaDto actualizar(ActualizarExamenPreguntaDto request) {
        return repositoryPort.actualizar(request);
    }
}

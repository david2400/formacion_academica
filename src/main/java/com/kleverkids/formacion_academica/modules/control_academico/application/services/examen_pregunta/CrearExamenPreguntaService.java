package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.CrearExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_pregunta.ExamenPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.CrearExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearExamenPreguntaService implements CrearExamenPreguntaUseCase {

    private final ExamenPreguntaRepositoryPort repositoryPort;

    @Override
    public ExamenPreguntaDto crear(CrearExamenPreguntaDto request) {
        return repositoryPort.guardar(request);
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.ConsultarExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_pregunta.ExamenPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ConsultarExamenPreguntaService implements ConsultarExamenPreguntaUseCase {

    private final ExamenPreguntaRepositoryPort repositoryPort;

    @Override
    public ExamenPreguntaDto consultarPorId(Long id) {
        return repositoryPort.obtenerPorId(id);
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services.examen_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.ListarExamenPreguntaPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_pregunta.ExamenPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarExamenPreguntaPorExamenService implements ListarExamenPreguntaPorExamenUseCase {

    private final ExamenPreguntaRepositoryPort repositoryPort;

    @Override
    public List<ExamenPreguntaDto> listarPorExamen(Long examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }
}

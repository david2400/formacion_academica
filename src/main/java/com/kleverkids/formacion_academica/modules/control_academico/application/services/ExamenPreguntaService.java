package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.ActualizarExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.ConsultarExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.CrearExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.EliminarExamenPreguntaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta.ListarExamenPreguntaPorExamenUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_pregunta.ExamenPreguntaRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ActualizarExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.CrearExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class ExamenPreguntaService implements CrearExamenPreguntaUseCase,
        ActualizarExamenPreguntaUseCase,
        ListarExamenPreguntaPorExamenUseCase,
        ConsultarExamenPreguntaUseCase,
        EliminarExamenPreguntaUseCase {

    private final ExamenPreguntaRepositoryPort repositoryPort;

    @Override
    public ExamenPreguntaDto crear(CrearExamenPreguntaDto request) {
        return repositoryPort.guardar(request);
    }

    @Override
    public ExamenPreguntaDto actualizar(ActualizarExamenPreguntaDto request) {
        return repositoryPort.actualizar(request);
    }

    @Override
    public List<ExamenPreguntaDto> listarPorExamen(Long examenId) {
        return repositoryPort.listarPorExamen(examenId);
    }

    @Override
    public ExamenPreguntaDto consultarPorId(Long id) {
        return repositoryPort.obtenerPorId(id);
    }

    @Override
    public void eliminar(Long id) {
        repositoryPort.obtenerPorId(id);
        repositoryPort.eliminar(id);
    }
}

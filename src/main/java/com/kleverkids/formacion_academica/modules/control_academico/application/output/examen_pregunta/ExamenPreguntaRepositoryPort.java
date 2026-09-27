package com.kleverkids.formacion_academica.modules.control_academico.application.output.examen_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ActualizarExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.CrearExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;

import java.util.List;

public interface ExamenPreguntaRepositoryPort {

    ExamenPreguntaDto guardar(CrearExamenPreguntaDto request);

    ExamenPreguntaDto actualizar(ActualizarExamenPreguntaDto request);

    List<ExamenPreguntaDto> listarPorExamen(Long examenId);

    ExamenPreguntaDto obtenerPorId(Long id);

    void eliminar(Long id);
}

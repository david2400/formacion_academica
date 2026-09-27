package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;

import java.util.List;

public interface ListarExamenPreguntaPorExamenUseCase {

    List<ExamenPreguntaDto> listarPorExamen(Long examenId);
}

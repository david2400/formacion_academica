package com.kleverkids.formacion_academica.modules.control_academico.application.input.examen_pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ActualizarExamenPreguntaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta.ExamenPreguntaDto;

public interface ActualizarExamenPreguntaUseCase {

    ExamenPreguntaDto actualizar(ActualizarExamenPreguntaDto request);
}

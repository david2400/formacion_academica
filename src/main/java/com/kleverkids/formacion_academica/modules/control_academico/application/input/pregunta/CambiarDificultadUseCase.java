package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaDto;

public interface CambiarDificultadUseCase {
    PreguntaDto cambiarDificultad(Long preguntaId, String nuevaDificultad);
}

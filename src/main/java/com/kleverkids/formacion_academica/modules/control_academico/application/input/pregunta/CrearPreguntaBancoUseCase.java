package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;

public interface CrearPreguntaBancoUseCase {

    PreguntaBancoDto crear(CrearPreguntaBancoDto request);
}

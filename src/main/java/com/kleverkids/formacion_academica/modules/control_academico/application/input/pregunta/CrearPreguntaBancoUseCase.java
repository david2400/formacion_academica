package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CrearPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.PreguntaBanco;

public interface CrearPreguntaBancoUseCase {

    PreguntaBanco crear(CrearPreguntaBancoDto request);
}

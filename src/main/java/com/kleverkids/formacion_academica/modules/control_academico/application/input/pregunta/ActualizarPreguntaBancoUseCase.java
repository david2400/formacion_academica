package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ActualizarPreguntaBancoDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;

public interface ActualizarPreguntaBancoUseCase {

    PreguntaBancoDto actualizar(ActualizarPreguntaBancoDto request);
}

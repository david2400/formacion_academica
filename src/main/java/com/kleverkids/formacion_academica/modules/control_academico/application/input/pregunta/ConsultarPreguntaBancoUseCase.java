package com.kleverkids.formacion_academica.modules.control_academico.application.input.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.PreguntaBancoDto;

import java.util.UUID;

public interface ConsultarPreguntaBancoUseCase {

    PreguntaBancoDto consultarPorId(UUID preguntaId);
}

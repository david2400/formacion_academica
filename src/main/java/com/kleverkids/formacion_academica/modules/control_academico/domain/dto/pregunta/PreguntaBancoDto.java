package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.List;
import java.util.UUID;

public record PreguntaBancoDto(UUID id,
                               UUID tematicaId,
                               String enunciado,
                               String tipo,
                               String nivelDificultad,
                               Integer puntaje,
                               List<RespuestaBancoDto> respuestas) {
}

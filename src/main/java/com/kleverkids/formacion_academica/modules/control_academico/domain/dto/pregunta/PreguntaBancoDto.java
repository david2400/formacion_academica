package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.List;

public record PreguntaBancoDto(Long id,
                               Long tematicaId,
                               String enunciado,
                               String tipo,
                               String nivelDificultad,
                               Integer puntaje,
                               List<RespuestaBancoDto> respuestas) {
}

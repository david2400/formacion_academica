package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.UUID;

public record ActualizarPreguntaBancoDto(UUID id,
                                         UUID tematicaId,
                                         String enunciado,
                                         String tipo,
                                         String nivelDificultad,
                                         Integer puntaje) {

    public ActualizarPreguntaBancoDto {
        if (id == null) {
            throw new IllegalArgumentException("El identificador de la pregunta es obligatorio");
        }
        if (tematicaId == null) {
            throw new IllegalArgumentException("La temática de la pregunta es obligatoria");
        }
        if (puntaje != null && puntaje < 1) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a 1");
        }
    }
}

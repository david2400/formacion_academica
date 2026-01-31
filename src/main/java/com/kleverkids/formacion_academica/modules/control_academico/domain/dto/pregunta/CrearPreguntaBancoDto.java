package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.List;
import java.util.UUID;

public record CrearPreguntaBancoDto(UUID tematicaId,
                                    String enunciado,
                                    String tipo,
                                    String nivelDificultad,
                                    Integer puntaje,
                                    List<CrearRespuestaBancoDto> respuestas) {

    public CrearPreguntaBancoDto {
        if (tematicaId == null) {
            throw new IllegalArgumentException("La temática es obligatoria");
        }
        if (enunciado == null || enunciado.isBlank()) {
            throw new IllegalArgumentException("El enunciado es obligatorio");
        }
        if (tipo == null || tipo.isBlank()) {
            throw new IllegalArgumentException("El tipo de pregunta es obligatorio");
        }
        if (puntaje == null || puntaje < 1) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a 1");
        }
    }
}

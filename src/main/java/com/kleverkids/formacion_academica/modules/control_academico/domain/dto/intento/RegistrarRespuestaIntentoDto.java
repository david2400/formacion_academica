package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import java.util.UUID;

public record RegistrarRespuestaIntentoDto(UUID intentoId,
                                           UUID preguntaId,
                                           String respuesta,
                                           Boolean esCorrecta,
                                           Integer puntajeObtenido) {

    public RegistrarRespuestaIntentoDto {
        if (intentoId == null) {
            throw new IllegalArgumentException("El intento es obligatorio");
        }
        if (preguntaId == null) {
            throw new IllegalArgumentException("La pregunta es obligatoria");
        }
        if (respuesta == null || respuesta.isBlank()) {
            throw new IllegalArgumentException("La respuesta es obligatoria");
        }
        if (puntajeObtenido != null && puntajeObtenido < 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}

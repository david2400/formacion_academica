package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import java.util.UUID;

public record FinalizarIntentoExamenDto(UUID intentoId,
                                        Integer puntajeTotal) {

    public FinalizarIntentoExamenDto {
        if (intentoId == null) {
            throw new IllegalArgumentException("El intento es obligatorio");
        }
        if (puntajeTotal != null && puntajeTotal < 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}

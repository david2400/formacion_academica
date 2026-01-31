package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica;

import java.util.UUID;

public record ActualizarTematicaExamenDto(UUID id,
                                          UUID examenId,
                                          String titulo,
                                          String descripcion,
                                          Integer orden) {

    public ActualizarTematicaExamenDto {
        if (id == null) {
            throw new IllegalArgumentException("El identificador de la temática es obligatorio");
        }
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (titulo != null && titulo.isBlank()) {
            throw new IllegalArgumentException("El título no puede estar vacío");
        }
        if (orden != null && orden < 1) {
            throw new IllegalArgumentException("El orden debe ser mayor o igual a 1");
        }
    }
}

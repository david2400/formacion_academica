package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica;

import java.util.UUID;

public record CrearTematicaExamenDto(UUID examenId,
                                     String titulo,
                                     String descripcion,
                                     Integer orden) {

    public CrearTematicaExamenDto {
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (titulo == null || titulo.isBlank()) {
            throw new IllegalArgumentException("El título es obligatorio");
        }
        if (orden == null || orden < 1) {
            throw new IllegalArgumentException("El orden debe ser mayor o igual a 1");
        }
    }
}

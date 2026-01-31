package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula;

import java.util.UUID;

public record ActualizarAulaDto(UUID id,
                                String nombre,
                                String descripcion,
                                Integer capacidad,
                                Boolean activo) {

    public ActualizarAulaDto {
        if (id == null) {
            throw new IllegalArgumentException("El identificador del aula es obligatorio");
        }
        if (capacidad != null && capacidad <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }
    }
}

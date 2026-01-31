package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo;

import java.util.UUID;

public record ActualizarGrupoDto(UUID id,
                                 String nombre,
                                 Integer capacidadMaxima,
                                 UUID tutorId,
                                 UUID aulaId,
                                 Boolean activo) {

    public ActualizarGrupoDto {
        if (id == null) {
            throw new IllegalArgumentException("El identificador del grupo es obligatorio");
        }
        if (capacidadMaxima != null && capacidadMaxima <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }
    }
}

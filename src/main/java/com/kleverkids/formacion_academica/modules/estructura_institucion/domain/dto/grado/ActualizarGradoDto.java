package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado;

import java.util.UUID;

public record ActualizarGradoDto(UUID id,
                                 String nombre,
                                 String nivelEducativo,
                                 Integer orden) {

    public ActualizarGradoDto {
        if (id == null) {
            throw new IllegalArgumentException("El identificador del grado es obligatorio");
        }
        if (orden != null && orden < 0) {
            throw new IllegalArgumentException("El orden debe ser positivo");
        }
    }
}

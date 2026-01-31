package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo;

import java.util.UUID;

public record CrearGrupoDto(String codigo,
                            String nombre,
                            UUID gradoId,
                            Integer capacidadMaxima,
                            UUID tutorId,
                            UUID aulaId) {

    public CrearGrupoDto {
        if (codigo == null || codigo.isBlank()) {
            throw new IllegalArgumentException("El código es obligatorio");
        }
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre es obligatorio");
        }
        if (gradoId == null) {
            throw new IllegalArgumentException("El grado es obligatorio");
        }
        if (capacidadMaxima != null && capacidadMaxima <= 0) {
            throw new IllegalArgumentException("La capacidad debe ser positiva");
        }
    }
}

package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.aula;

public record CrearAulaDto(String nombre,
                           String descripcion,
                           Integer capacidad,
                           Boolean activo) {

    public CrearAulaDto {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del aula es obligatorio");
        }
    }
}

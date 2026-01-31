package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado;

public record CrearGradoDto(String nombre,
                            String nivelEducativo,
                            Integer orden) {

    public CrearGradoDto {
        if (nombre == null || nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del grado es obligatorio");
        }
        if (nivelEducativo == null || nivelEducativo.isBlank()) {
            throw new IllegalArgumentException("El nivel educativo es obligatorio");
        }
        if (orden != null && orden < 0) {
            throw new IllegalArgumentException("El orden debe ser positivo");
        }
    }
}

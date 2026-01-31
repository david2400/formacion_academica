package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente;

import java.util.UUID;

public record CrearEstudianteAcudienteDto(UUID estudianteId,
                                          UUID acudienteId,
                                          String parentesco,
                                          boolean esPrincipal) {

    public CrearEstudianteAcudienteDto {
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (acudienteId == null) {
            throw new IllegalArgumentException("El acudiente es obligatorio");
        }
        if (parentesco == null || parentesco.isBlank()) {
            throw new IllegalArgumentException("El parentesco es obligatorio");
        }
    }
}

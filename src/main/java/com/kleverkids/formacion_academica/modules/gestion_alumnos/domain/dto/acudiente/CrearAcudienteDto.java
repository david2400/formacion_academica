package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente;

import java.util.UUID;

public record CrearAcudienteDto(UUID estudianteId,
                                String tipoDocumento,
                                String numeroDocumento,
                                String nombres,
                                String apellidos,
                                String parentesco,
                                String telefono,
                                String correo,
                                boolean esPrincipal) {

    public CrearAcudienteDto {
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (numeroDocumento == null || numeroDocumento.isBlank()) {
            throw new IllegalArgumentException("El número de documento es obligatorio");
        }
        if (nombres == null || nombres.isBlank()) {
            throw new IllegalArgumentException("El nombre del acudiente es obligatorio");
        }
        if (parentesco == null || parentesco.isBlank()) {
            throw new IllegalArgumentException("El parentesco es obligatorio");
        }
    }
}

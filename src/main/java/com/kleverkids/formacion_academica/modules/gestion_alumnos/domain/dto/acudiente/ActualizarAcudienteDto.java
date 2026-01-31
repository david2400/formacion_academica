package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente;

import java.util.UUID;

public record ActualizarAcudienteDto(UUID acudienteId,
                                     UUID estudianteId,
                                     String tipoDocumento,
                                     String numeroDocumento,
                                     String nombres,
                                     String apellidos,
                                     String parentesco,
                                     String telefono,
                                     String correo,
                                     Boolean esPrincipal) {

    public ActualizarAcudienteDto {
        if (acudienteId == null) {
            throw new IllegalArgumentException("El identificador del acudiente es obligatorio");
        }
    }
}

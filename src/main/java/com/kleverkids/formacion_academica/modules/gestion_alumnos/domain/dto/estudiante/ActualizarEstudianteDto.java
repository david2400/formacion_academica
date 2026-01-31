package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante;

import java.time.LocalDate;
import java.util.UUID;

public record ActualizarEstudianteDto(UUID estudianteId,
                                      String tipoDocumento,
                                      String numeroDocumento,
                                      String nombres,
                                      String apellidos,
                                      LocalDate fechaNacimiento,
                                      String genero,
                                      String correo,
                                      String telefono,
                                      String direccion) {

    public ActualizarEstudianteDto {
        if (estudianteId == null) {
            throw new IllegalArgumentException("El identificador del estudiante es obligatorio");
        }
    }
}

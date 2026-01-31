package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante;

import java.time.LocalDate;

public record CrearEstudianteDto(String tipoDocumento,
                                 String numeroDocumento,
                                 String nombres,
                                 String apellidos,
                                 LocalDate fechaNacimiento,
                                 String genero,
                                 String correo,
                                 String telefono,
                                 String direccion) {

    public CrearEstudianteDto {
        if (numeroDocumento == null || numeroDocumento.isBlank()) {
            throw new IllegalArgumentException("El número de documento es obligatorio");
        }
        if (nombres == null || nombres.isBlank()) {
            throw new IllegalArgumentException("El nombre del estudiante es obligatorio");
        }
        if (apellidos == null || apellidos.isBlank()) {
            throw new IllegalArgumentException("El apellido del estudiante es obligatorio");
        }
    }
}

package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import java.time.LocalDate;

public record Estudiante(Long id,
                         String tipoDocumento,
                         String numeroDocumento,
                         String nombres,
                         String apellidos,
                         LocalDate fechaNacimiento,
                         String genero,
                         String correo,
                         String telefono,
                         String direccion,
                         boolean activo) {
}

package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente;

public record AcudienteDto(Long id,
                           Long estudianteId,
                           String tipoDocumento,
                           String numeroDocumento,
                           String nombres,
                           String apellidos,
                           String parentesco,
                           String telefono,
                           String correo,
                           boolean esPrincipal,
                           boolean activo) {
}

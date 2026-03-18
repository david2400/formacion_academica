package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

public record Acudiente(Long id,
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

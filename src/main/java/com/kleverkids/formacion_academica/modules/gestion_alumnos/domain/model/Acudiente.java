package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import java.time.Instant;

public record Acudiente(Long id,
                        String tipoDocumento,
                        String numeroDocumento,
                        String nombres,
                        String apellidos,
                        String parentesco,
                        String telefono,
                        String correo,
                        boolean esPrincipal,
                        boolean activo,
                        Integer usrCrea,
                        Integer usrMod,
                        Instant createdAt,
                        Instant updatedAt) {
}

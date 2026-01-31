package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.acudiente;

import java.util.UUID;

public record Acudiente(UUID id,
                        UUID estudianteId,
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

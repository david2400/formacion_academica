package com.kleverkids.formacion_academica.modules.usuarios.domain.dto.profesor;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.time.LocalDate;

/**
 * Profesor con la identidad de su usuario (leída de access_control en la misma consulta).
 * Los campos de identidad son null si el usuario ya no existe allá.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfesorDto {
    private Long id;
    private Long usuarioId;
    private Long empresaId;
    private boolean activo;
    private LocalDate fechaVinculacion;
    private String observaciones;
    private Instant createdAt;

    private String usuario;
    private String nombreCompleto;
    private String tipoDocumento;
    private String numeroDocumento;
    private String estadoUsuario;
}

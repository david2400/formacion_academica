package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Credenciales de login de un Acudiente: documento + contraseña propia
 * (mismo mecanismo que {@link LoginEstudianteDto}, ver
 * {@link com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.Acudiente#getPassword()}).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginAcudienteDto {

    @NotBlank(message = "El tipo de documento es obligatorio")
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "La contraseña es obligatoria")
    private String password;
}

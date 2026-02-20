package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarAcudienteDto {

    @NotNull(message = "El identificador del acudiente es obligatorio")
    private UUID acudienteId;
    private UUID estudianteId;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private String parentesco;
    private String telefono;

    @Email(message = "El correo debe ser válido")
    private String correo;
    private Boolean esPrincipal;

    public UUID acudienteId() {
        return acudienteId;
    }

    public UUID estudianteId() {
        return estudianteId;
    }

    public String tipoDocumento() {
        return tipoDocumento;
    }

    public String numeroDocumento() {
        return numeroDocumento;
    }

    public String nombres() {
        return nombres;
    }

    public String apellidos() {
        return apellidos;
    }

    public String parentesco() {
        return parentesco;
    }

    public String telefono() {
        return telefono;
    }

    public String correo() {
        return correo;
    }

    public Boolean esPrincipal() {
        return esPrincipal;
    }
}

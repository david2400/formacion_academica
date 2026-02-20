package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
public class CrearAcudienteDto {

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;
    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "El nombre del acudiente es obligatorio")
    private String nombres;
    private String apellidos;

    @NotBlank(message = "El parentesco es obligatorio")
    private String parentesco;
    private String telefono;

    @Email(message = "El correo debe ser válido")
    private String correo;
    private boolean esPrincipal;

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

    public boolean esPrincipal() {
        return esPrincipal;
    }
}

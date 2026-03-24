package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.acudiente;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearAcudienteDto {
    
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

}

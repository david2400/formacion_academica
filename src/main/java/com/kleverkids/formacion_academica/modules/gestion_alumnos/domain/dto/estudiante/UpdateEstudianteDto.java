package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateEstudianteDto {

    @NotNull(message = "El identificador del estudiante es obligatorio")
    private UUID estudianteId;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String genero;

    @Email(message = "El correo debe ser válido")
    private String correo;
    private String telefono;
    private String direccion;

}

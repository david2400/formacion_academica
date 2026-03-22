package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearEstudianteDto {

    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String nombres;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    private String primerApellido;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    private String segundoApellido;

    private LocalDate fechaNacimiento;
    private String genero;

    @Email(message = "El correo debe ser válido")
    private String correo;
    private String telefono;
    private String direccion;
}

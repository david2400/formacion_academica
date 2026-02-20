package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearEstudianteDto {

    private String tipoDocumento;

    @NotBlank(message = "El número de documento es obligatorio")
    private String numeroDocumento;

    @NotBlank(message = "El nombre del estudiante es obligatorio")
    private String nombres;

    @NotBlank(message = "El apellido del estudiante es obligatorio")
    private String apellidos;

    private LocalDate fechaNacimiento;
    private String genero;

    @Email(message = "El correo debe ser válido")
    private String correo;
    private String telefono;
    private String direccion;

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

    public LocalDate fechaNacimiento() {
        return fechaNacimiento;
    }

    public String genero() {
        return genero;
    }

    public String correo() {
        return correo;
    }

    public String telefono() {
        return telefono;
    }

    public String direccion() {
        return direccion;
    }
}

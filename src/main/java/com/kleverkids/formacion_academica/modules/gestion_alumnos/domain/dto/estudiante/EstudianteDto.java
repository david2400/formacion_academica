package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante;

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
public class EstudianteDto {

    private UUID id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String apellidos;
    private LocalDate fechaNacimiento;
    private String genero;
    private String correo;
    private String telefono;
    private String direccion;
    private boolean activo;

}

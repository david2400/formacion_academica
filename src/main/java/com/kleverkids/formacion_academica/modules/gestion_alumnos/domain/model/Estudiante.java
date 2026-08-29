package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import java.time.Instant;
import java.time.LocalDate;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Estudiante {
    private Long id;
    private String tipoDocumento;
    private String numeroDocumento;
    private String nombres;
    private String primerApellido;
    private String segundoApellido;
    private LocalDate fechaNacimiento;
    private String genero;
    private String correo;
    private String telefono;
    private String direccion;
    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private Instant createdAt;
    private Instant updatedAt;
}

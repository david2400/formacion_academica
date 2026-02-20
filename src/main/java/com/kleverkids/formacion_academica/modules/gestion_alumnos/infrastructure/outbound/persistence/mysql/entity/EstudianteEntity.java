package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Entity
@Table(name = "estudiantes")
public class EstudianteEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private String tipoDocumento;

    @Column(nullable = false, unique = true)
    private String numeroDocumento;

    @Column(nullable = false)
    private String nombres;

    @Column(nullable = false)
    private String apellidos;

    private LocalDate fechaNacimiento;

    private String genero;

    private String correo;

    private String telefono;

    private String direccion;

    @Column(nullable = false)
    private boolean activo;

}

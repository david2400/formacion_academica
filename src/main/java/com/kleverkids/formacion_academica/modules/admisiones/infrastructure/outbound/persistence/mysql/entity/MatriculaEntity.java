package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.time.LocalDate;

@Data
@Entity
@Table(name = "matriculas")
public class MatriculaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long inscripcionId;

    @Column(nullable = false)
    private Long estudianteId;

    private Long gradoId;

    private Long grupoId;

    @Column(nullable = false)
    private LocalDate fechaMatricula;

    @Column(nullable = false)
    private boolean renovacion;

    @Column(nullable = false)
    private String estado;

    private String observaciones;
}

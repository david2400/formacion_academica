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
@Table(name = "inscripciones")
public class InscripcionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "estudiante_id")
    private Long estudianteId;

    @Column(nullable = false, name = "periodo_academico")
    private String periodoAcademico;

    @Column(nullable = false, name = "fecha_solicitud")
    private LocalDate fechaSolicitud;

    @Column(nullable = false, name = "estado_id")
    private Integer estadoId;

    @Column(nullable = false)
    private String observaciones;

}

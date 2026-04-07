package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "matriculas")
@EqualsAndHashCode(callSuper = true)
public class MatriculaEntity extends AuditInfo {

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
    private Integer estadoId;

    private String observaciones;
}

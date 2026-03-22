package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@Entity
@SuperBuilder
@Table(name = "estudiante_acudiente")
public class EstudianteAcudienteEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long estudianteId;

    @Column(nullable = false)
    private Long acudienteId;

    @Column(nullable = false)
    private String parentesco;

    @Column(nullable = false)
    private boolean esPrincipal;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDate fechaVinculacion;

    @Column(nullable = false)
    private LocalDate fechaFin;


}

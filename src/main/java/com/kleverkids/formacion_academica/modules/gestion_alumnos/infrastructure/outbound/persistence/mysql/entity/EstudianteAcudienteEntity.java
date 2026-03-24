package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.EstudianteExamenEntity;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
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

    @Column(name = "parentesco_id", nullable = false)
    private Long parentescoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentesco_id", nullable = false, insertable = false, updatable = false)
    private ParentescoEntity parentesco;

    @Column(nullable = false)
    private boolean esPrincipal;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDate fechaVinculacion;

    @Column(nullable = false)
    private LocalDate fechaFin;


}

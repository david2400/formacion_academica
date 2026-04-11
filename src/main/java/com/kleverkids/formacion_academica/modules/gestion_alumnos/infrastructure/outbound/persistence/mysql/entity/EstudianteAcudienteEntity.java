package com.kleverkids.formacion_academica.modules.gestion_alumnos.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.EstudianteExamenEntity;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "estudiante_acudiente")
public class EstudianteAcudienteEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, name = "estudiante_id")
    private Long estudianteId;

    @Column(nullable = false, name = "acudiente_id")
    private Long acudienteId;

    @Column(name = "parentesco_id", nullable = false)
    private Long parentescoId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "parentesco_id", nullable = false, insertable = false, updatable = false)
    private ParentescoEntity parentesco;

    @Column(nullable = false, name = "es_principal")
    private boolean esPrincipal;

    @Column(nullable = false, name = "estado_id")
    private Integer estadoId;

    @Column(nullable = false, name = "fecha_vinculacion")
    private LocalDate fechaVinculacion;

    @Column(nullable = false, name = "fecha_fin")
    private LocalDate fechaFin;

}

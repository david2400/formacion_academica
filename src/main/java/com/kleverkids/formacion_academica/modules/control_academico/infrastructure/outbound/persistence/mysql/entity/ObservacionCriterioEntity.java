package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@EqualsAndHashCode(callSuper = true)
@SuperBuilder
@NoArgsConstructor
@Entity
@Table(name = "observaciones_criterio_examen")
public class ObservacionCriterioEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "examen_id", nullable = false)
    private Long examenId;

    @Column(name = "criterio_examen_id", nullable = false)
    private Long criterioExamenId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "criterio_examen_id", nullable = false, insertable = false, updatable = false)
    private CriterioExamenEntity criterioExamen;

    @Column(name = "estudiante_id",nullable = false)
    private Long estudianteId;

    @Column(name = "observacion", nullable = false)
    private String observacion;

    @Column(name = "recomendacion", nullable = false)
    private String recomendacion;
}

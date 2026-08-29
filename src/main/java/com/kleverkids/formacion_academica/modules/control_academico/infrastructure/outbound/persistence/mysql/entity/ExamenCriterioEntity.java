package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity;

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

import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "examenes_criterios")
public class ExamenCriterioEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "examen_id", nullable = false)
    private Long examenId;

    @Column(name = "criterio_evaluado_id", nullable = false)
    private Long criterioEvaluadoId;

    @Column(name = "ponderacion", nullable = false)
    private BigDecimal ponderacion;

    @Column(name = "orden", nullable = false)
    private Integer orden;

}
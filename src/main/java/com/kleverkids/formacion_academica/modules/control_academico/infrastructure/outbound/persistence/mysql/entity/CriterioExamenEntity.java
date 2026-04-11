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

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "criterios_examen")
public class CriterioExamenEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "examen_id",nullable = false)
    private Long examenId;

    @Column(name = "nombre", nullable = false)
    private String nombre;

    @Column(name = "descripcion", nullable = false)
    private String descripcion;

    @Column(name = "ponderacion", nullable = false)
    private BigDecimal ponderacion;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    @Column(name = "recomendacion_base",nullable = false)
    private String recomendacionBase;

}

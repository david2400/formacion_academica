package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;


@Data
@Entity
@Table(name = "calificaciones_personalizadas")
public class CalificacionPersonalizadaEntity extends AuditInfo {

    @Id
    private Long id;

    @Column(nullable = false)
    private Long examenId;

    @Column(nullable = false)
    private Long estudianteId;

    @Column(nullable = false)
    private String criterio;

    @Column(nullable = false)
    private BigDecimal puntajeOtorgado;

}

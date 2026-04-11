package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
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
@Table(name = "calificaciones_personalizadas")
public class CalificacionPersonalizadaEntity extends AuditInfo {

    @Id
    private Long id;

    @Column(name = "examen_id", nullable = false)
    private Long examenId;

    @Column(name = "estudiante_id", nullable = false)
    private Long estudianteId;

    @Column(name = "criterio", nullable = false)
    private String criterio;

    @Column(name = "puntaje_otorgado", nullable = false)
    private BigDecimal puntajeOtorgado;

}

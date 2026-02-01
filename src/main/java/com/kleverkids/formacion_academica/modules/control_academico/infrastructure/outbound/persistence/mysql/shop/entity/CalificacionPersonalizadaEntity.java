package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@Entity
@Table(name = "calificaciones_personalizadas")
public class CalificacionPersonalizadaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID examenId;

    @Column(nullable = false)
    private UUID estudianteId;

    @Column(nullable = false)
    private String criterio;

    @Column(nullable = false)
    private BigDecimal puntajeOtorgado;

}

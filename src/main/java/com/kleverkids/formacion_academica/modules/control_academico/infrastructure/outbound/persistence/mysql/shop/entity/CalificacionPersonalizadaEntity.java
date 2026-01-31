package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.util.UUID;

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

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getExamenId() {
        return examenId;
    }

    public void setExamenId(UUID examenId) {
        this.examenId = examenId;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(UUID estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getCriterio() {
        return criterio;
    }

    public void setCriterio(String criterio) {
        this.criterio = criterio;
    }

    public BigDecimal getPuntajeOtorgado() {
        return puntajeOtorgado;
    }

    public void setPuntajeOtorgado(BigDecimal puntajeOtorgado) {
        this.puntajeOtorgado = puntajeOtorgado;
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "respuestas_criterio_examen")
public class RespuestaCriterioEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_examen_id", nullable = false)
    private EstudianteExamenEntity estudianteExamen;

    @Column(nullable = false)
    private UUID examenId;

    @Column(nullable = false)
    private UUID criterioId;

    @Column(nullable = false)
    private UUID estudianteId;

    @Column(nullable = false)
    private String respuesta;

    private BigDecimal puntajeObtenido;

    @Column(nullable = false)
    private LocalDateTime registradaEn;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public EstudianteExamenEntity getEstudianteExamen() {
        return estudianteExamen;
    }

    public void setEstudianteExamen(EstudianteExamenEntity estudianteExamen) {
        this.estudianteExamen = estudianteExamen;
    }

    public UUID getExamenId() {
        return examenId;
    }

    public void setExamenId(UUID examenId) {
        this.examenId = examenId;
    }

    public UUID getCriterioId() {
        return criterioId;
    }

    public void setCriterioId(UUID criterioId) {
        this.criterioId = criterioId;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(UUID estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public BigDecimal getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(BigDecimal puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }

    public LocalDateTime getRegistradaEn() {
        return registradaEn;
    }

    public void setRegistradaEn(LocalDateTime registradaEn) {
        this.registradaEn = registradaEn;
    }
}

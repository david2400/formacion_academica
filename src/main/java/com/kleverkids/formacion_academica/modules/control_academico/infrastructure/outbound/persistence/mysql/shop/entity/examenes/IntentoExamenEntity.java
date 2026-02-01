package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "intentos_examen")
public class IntentoExamenEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID examenId;

    @Column(nullable = false)
    private UUID estudianteId;

    @Column(nullable = false)
    private String estado;

    @Column(nullable = false)
    private LocalDateTime iniciadoEn;

    private LocalDateTime finalizadoEn;

    private Integer puntajeTotal;

    @OneToMany(mappedBy = "intento", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RespuestaIntentoEntity> respuestas = new ArrayList<>();

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

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDateTime getIniciadoEn() {
        return iniciadoEn;
    }

    public void setIniciadoEn(LocalDateTime iniciadoEn) {
        this.iniciadoEn = iniciadoEn;
    }

    public LocalDateTime getFinalizadoEn() {
        return finalizadoEn;
    }

    public void setFinalizadoEn(LocalDateTime finalizadoEn) {
        this.finalizadoEn = finalizadoEn;
    }

    public Integer getPuntajeTotal() {
        return puntajeTotal;
    }

    public void setPuntajeTotal(Integer puntajeTotal) {
        this.puntajeTotal = puntajeTotal;
    }

    public List<RespuestaIntentoEntity> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaIntentoEntity> respuestas) {
        this.respuestas = respuestas;
    }
}

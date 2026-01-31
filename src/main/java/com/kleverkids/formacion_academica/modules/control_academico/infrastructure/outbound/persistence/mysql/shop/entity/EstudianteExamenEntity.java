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
@Table(name = "estudiantes_examen")
public class EstudianteExamenEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID examenId;

    @Column(nullable = false)
    private UUID estudianteId;

    @Column(nullable = false)
    private LocalDateTime asignadoEn;

    @OneToMany(mappedBy = "estudianteExamen", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RespuestaCriterioEntity> respuestas = new ArrayList<>();

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

    public LocalDateTime getAsignadoEn() {
        return asignadoEn;
    }

    public void setAsignadoEn(LocalDateTime asignadoEn) {
        this.asignadoEn = asignadoEn;
    }

    public List<RespuestaCriterioEntity> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaCriterioEntity> respuestas) {
        this.respuestas = respuestas;
    }
}

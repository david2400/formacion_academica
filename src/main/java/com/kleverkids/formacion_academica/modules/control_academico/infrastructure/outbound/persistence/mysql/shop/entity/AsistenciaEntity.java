package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "asistencias")
public class AsistenciaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID claseId;

    @Column(nullable = false)
    private UUID estudianteId;

    @Column(nullable = false)
    private LocalDateTime fechaRegistro;

    @Column(nullable = false)
    private boolean presente;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getClaseId() {
        return claseId;
    }

    public void setClaseId(UUID claseId) {
        this.claseId = claseId;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(UUID estudianteId) {
        this.estudianteId = estudianteId;
    }

    public LocalDateTime getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDateTime fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }

    public boolean isPresente() {
        return presente;
    }

    public void setPresente(boolean presente) {
        this.presente = presente;
    }
}

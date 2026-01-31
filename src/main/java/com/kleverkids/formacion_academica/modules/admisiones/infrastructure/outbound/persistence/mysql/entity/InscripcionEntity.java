package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "inscripciones")
public class InscripcionEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID estudianteId;

    @Column(nullable = false)
    private String periodoAcademico;

    @Column(nullable = false)
    private LocalDate fechaSolicitud;

    @Column(nullable = false)
    private String estado;

    private String observaciones;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(UUID estudianteId) {
        this.estudianteId = estudianteId;
    }

    public String getPeriodoAcademico() {
        return periodoAcademico;
    }

    public void setPeriodoAcademico(String periodoAcademico) {
        this.periodoAcademico = periodoAcademico;
    }

    public LocalDate getFechaSolicitud() {
        return fechaSolicitud;
    }

    public void setFechaSolicitud(LocalDate fechaSolicitud) {
        this.fechaSolicitud = fechaSolicitud;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getObservaciones() {
        return observaciones;
    }

    public void setObservaciones(String observaciones) {
        this.observaciones = observaciones;
    }
}

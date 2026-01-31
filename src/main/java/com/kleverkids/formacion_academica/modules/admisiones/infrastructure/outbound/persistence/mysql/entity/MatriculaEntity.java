package com.kleverkids.formacion_academica.modules.admisiones.infrastructure.outbound.persistence.mysql.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Table(name = "matriculas")
public class MatriculaEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID inscripcionId;

    @Column(nullable = false)
    private UUID estudianteId;

    private UUID gradoId;

    private UUID grupoId;

    @Column(nullable = false)
    private LocalDate fechaMatricula;

    @Column(nullable = false)
    private boolean renovacion;

    @Column(nullable = false)
    private String estado;

    private String observaciones;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getInscripcionId() {
        return inscripcionId;
    }

    public void setInscripcionId(UUID inscripcionId) {
        this.inscripcionId = inscripcionId;
    }

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(UUID estudianteId) {
        this.estudianteId = estudianteId;
    }

    public UUID getGradoId() {
        return gradoId;
    }

    public void setGradoId(UUID gradoId) {
        this.gradoId = gradoId;
    }

    public UUID getGrupoId() {
        return grupoId;
    }

    public void setGrupoId(UUID grupoId) {
        this.grupoId = grupoId;
    }

    public LocalDate getFechaMatricula() {
        return fechaMatricula;
    }

    public void setFechaMatricula(LocalDate fechaMatricula) {
        this.fechaMatricula = fechaMatricula;
    }

    public boolean isRenovacion() {
        return renovacion;
    }

    public void setRenovacion(boolean renovacion) {
        this.renovacion = renovacion;
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

package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "examenes")
public class ExamenEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID claseId;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false)
    private LocalDate fecha;

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "examen_reglas", joinColumns = @JoinColumn(name = "examen_id"))
    private List<ReglaCalificacionEmbeddable> reglas;

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

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public List<ReglaCalificacionEmbeddable> getReglas() {
        return reglas;
    }

    public void setReglas(List<ReglaCalificacionEmbeddable> reglas) {
        this.reglas = reglas;
    }

    @Embeddable
    public static class ReglaCalificacionEmbeddable {

        private String criterio;
        private BigDecimal ponderacion;
        private BigDecimal puntajeMaximo;

        public String getCriterio() {
            return criterio;
        }

        public void setCriterio(String criterio) {
            this.criterio = criterio;
        }

        public BigDecimal getPonderacion() {
            return ponderacion;
        }

        public void setPonderacion(BigDecimal ponderacion) {
            this.ponderacion = ponderacion;
        }

        public BigDecimal getPuntajeMaximo() {
            return puntajeMaximo;
        }

        public void setPuntajeMaximo(BigDecimal puntajeMaximo) {
            this.puntajeMaximo = puntajeMaximo;
        }
    }
}

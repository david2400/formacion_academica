package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "preguntas_banco")
public class PreguntaBancoEntity {

    @Id
    private UUID id;

    @Column(nullable = false)
    private UUID tematicaId;

    @Column(nullable = false, length = 1000)
    private String enunciado;

    @Column(nullable = false)
    private String tipo;

    private String nivelDificultad;

    @Column(nullable = false)
    private Integer puntaje;

    @OneToMany(mappedBy = "pregunta", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<RespuestaBancoEntity> respuestas = new ArrayList<>();

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getTematicaId() {
        return tematicaId;
    }

    public void setTematicaId(UUID tematicaId) {
        this.tematicaId = tematicaId;
    }

    public String getEnunciado() {
        return enunciado;
    }

    public void setEnunciado(String enunciado) {
        this.enunciado = enunciado;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNivelDificultad() {
        return nivelDificultad;
    }

    public void setNivelDificultad(String nivelDificultad) {
        this.nivelDificultad = nivelDificultad;
    }

    public Integer getPuntaje() {
        return puntaje;
    }

    public void setPuntaje(Integer puntaje) {
        this.puntaje = puntaje;
    }

    public List<RespuestaBancoEntity> getRespuestas() {
        return respuestas;
    }

    public void setRespuestas(List<RespuestaBancoEntity> respuestas) {
        this.respuestas = respuestas;
    }
}

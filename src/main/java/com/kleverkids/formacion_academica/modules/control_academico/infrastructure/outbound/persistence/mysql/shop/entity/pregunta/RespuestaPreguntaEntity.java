package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.EstudianteExamenEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "respuestas_pregunta_examen")
public class RespuestaPreguntaEntity {

    @Id
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "estudiante_examen_id", nullable = false)
    private EstudianteExamenEntity estudianteExamen;

    @Column(nullable = false)
    private UUID examenId;

    @Column(nullable = false)
    private UUID estudianteId;

    @Column(nullable = false)
    private UUID preguntaId;

    private UUID respuestaBancoId;

    @Column(length = 1000)
    private String respuestaTexto;

    private Boolean esCorrecta;

    private Integer puntajeObtenido;

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

    public UUID getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(UUID estudianteId) {
        this.estudianteId = estudianteId;
    }

    public UUID getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(UUID preguntaId) {
        this.preguntaId = preguntaId;
    }

    public UUID getRespuestaBancoId() {
        return respuestaBancoId;
    }

    public void setRespuestaBancoId(UUID respuestaBancoId) {
        this.respuestaBancoId = respuestaBancoId;
    }

    public String getRespuestaTexto() {
        return respuestaTexto;
    }

    public void setRespuestaTexto(String respuestaTexto) {
        this.respuestaTexto = respuestaTexto;
    }

    public Boolean getEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(Boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public Integer getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(Integer puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }

    public LocalDateTime getRegistradaEn() {
        return registradaEn;
    }

    public void setRegistradaEn(LocalDateTime registradaEn) {
        this.registradaEn = registradaEn;
    }
}

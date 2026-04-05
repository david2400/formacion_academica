package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.EstudianteExamenEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "respuestas_pregunta_examen")
public class RespuestaPreguntaEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "estudiante_examen_id", nullable = false, updatable = false, insertable = false)
    private EstudianteExamenEntity estudianteExamen;

    @Column(nullable = false)
    private Long examenId;

    @Column(nullable = false)
    private Long estudianteId;

    @Column(nullable = false, name = "pregunta_id")
    private Long preguntaId;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "pregunta_id", referencedColumnName = "id", insertable = false, updatable = false)
    private PreguntaEntity pregunta;

    private Long respuestaBancoId;

    @Column(length = 1000)
    private String respuestaTexto;

    private Boolean esCorrecta;

    private Integer puntajeObtenido;

    @Column(nullable = false)
    private LocalDateTime registradaEn;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstudianteExamenEntity getEstudianteExamen() {
        return estudianteExamen;
    }

    public void setEstudianteExamen(EstudianteExamenEntity estudianteExamen) {
        this.estudianteExamen = estudianteExamen;
    }

    public Long getExamenId() {
        return examenId;
    }

    public void setExamenId(Long examenId) {
        this.examenId = examenId;
    }

    public Long getEstudianteId() {
        return estudianteId;
    }

    public void setEstudianteId(Long estudianteId) {
        this.estudianteId = estudianteId;
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }

    public PreguntaEntity getPregunta() {
        return pregunta;
    }

    public void setPregunta(PreguntaEntity pregunta) {
        this.pregunta = pregunta;
    }

    public Long getRespuestaBancoId() {
        return respuestaBancoId;
    }

    public void setRespuestaBancoId(Long respuestaBancoId) {
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

package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes.IntentoExamenEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;



@Entity
@Table(name = "respuestas_intento_examen")
public class RespuestaIntentoEntity {

    @Id
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "intento_id", nullable = false)
    private IntentoExamenEntity intento;

    @Column(nullable = false)
    private Long preguntaId;

    @Column(nullable = false, length = 1000)
    private String respuesta;

    @Column(nullable = false)
    private boolean esCorrecta;

    private Integer puntajeObtenido;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IntentoExamenEntity getIntento() {
        return intento;
    }

    public void setIntento(IntentoExamenEntity intento) {
        this.intento = intento;
    }

    public Long getPreguntaId() {
        return preguntaId;
    }

    public void setPreguntaId(Long preguntaId) {
        this.preguntaId = preguntaId;
    }

    public String getRespuesta() {
        return respuesta;
    }

    public void setRespuesta(String respuesta) {
        this.respuesta = respuesta;
    }

    public boolean isEsCorrecta() {
        return esCorrecta;
    }

    public void setEsCorrecta(boolean esCorrecta) {
        this.esCorrecta = esCorrecta;
    }

    public Integer getPuntajeObtenido() {
        return puntajeObtenido;
    }

    public void setPuntajeObtenido(Integer puntajeObtenido) {
        this.puntajeObtenido = puntajeObtenido;
    }
}

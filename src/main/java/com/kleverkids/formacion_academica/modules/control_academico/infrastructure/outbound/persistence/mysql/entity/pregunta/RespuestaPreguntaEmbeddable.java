package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import java.math.BigDecimal;

public class RespuestaPreguntaEmbeddable {
    
    private Long id;
    private Long preguntaId;
    private Object respuesta;
    private BigDecimal puntaje;
    private boolean calificado;
    private String retroalimentacion;
    
    public RespuestaPreguntaEmbeddable() {}
    
    public RespuestaPreguntaEmbeddable(Long id, Long preguntaId, Object respuesta, BigDecimal puntaje, boolean calificado, String retroalimentacion) {
        this.id = id;
        this.preguntaId = preguntaId;
        this.respuesta = respuesta;
        this.puntaje = puntaje;
        this.calificado = calificado;
        this.retroalimentacion = retroalimentacion;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPreguntaId() { return preguntaId; }
    public void setPreguntaId(Long preguntaId) { this.preguntaId = preguntaId; }
    
    public Object getRespuesta() { return respuesta; }
    public void setRespuesta(Object respuesta) { this.respuesta = respuesta; }
    
    public BigDecimal getPuntaje() { return puntaje; }
    public void setPuntaje(BigDecimal puntaje) { this.puntaje = puntaje; }
    
    public boolean isCalificado() { return calificado; }
    public void setCalificado(boolean calificado) { this.calificado = calificado; }
    
    public String getRetroalimentacion() { return retroalimentacion; }
    public void setRetroalimentacion(String retroalimentacion) { this.retroalimentacion = retroalimentacion; }
}

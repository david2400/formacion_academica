package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import java.math.BigDecimal;

public class ResultadoPreguntaEmbeddable {
    
    private Long preguntaId;
    private BigDecimal puntaje;
    private BigDecimal puntajeMaximo;
    private boolean correcto;
    private String retroalimentacion;
    
    public ResultadoPreguntaEmbeddable() {}
    
    public ResultadoPreguntaEmbeddable(Long preguntaId, BigDecimal puntaje, BigDecimal puntajeMaximo, boolean correcto, String retroalimentacion) {
        this.preguntaId = preguntaId;
        this.puntaje = puntaje;
        this.puntajeMaximo = puntajeMaximo;
        this.correcto = correcto;
        this.retroalimentacion = retroalimentacion;
    }
    
    public Long getPreguntaId() { return preguntaId; }
    public void setPreguntaId(Long preguntaId) { this.preguntaId = preguntaId; }
    
    public BigDecimal getPuntaje() { return puntaje; }
    public void setPuntaje(BigDecimal puntaje) { this.puntaje = puntaje; }
    
    public BigDecimal getPuntajeMaximo() { return puntajeMaximo; }
    public void setPuntajeMaximo(BigDecimal puntajeMaximo) { this.puntajeMaximo = puntajeMaximo; }
    
    public boolean isCorrecto() { return correcto; }
    public void setCorrecto(boolean correcto) { this.correcto = correcto; }
    
    public String getRetroalimentacion() { return retroalimentacion; }
    public void setRetroalimentacion(String retroalimentacion) { this.retroalimentacion = retroalimentacion; }
}

package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.examenes;

import java.math.BigDecimal;

public class PreguntaExamenEmbeddable {
    
    private Long id;
    private Long preguntaId;
    private int orden;
    private BigDecimal puntos;
    private boolean obligatoria;
    
    public PreguntaExamenEmbeddable() {}
    
    public PreguntaExamenEmbeddable(Long id, Long preguntaId, int orden, BigDecimal puntos, boolean obligatoria) {
        this.id = id;
        this.preguntaId = preguntaId;
        this.orden = orden;
        this.puntos = puntos;
        this.obligatoria = obligatoria;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getPreguntaId() { return preguntaId; }
    public void setPreguntaId(Long preguntaId) { this.preguntaId = preguntaId; }
    
    public int getOrden() { return orden; }
    public void setOrden(int orden) { this.orden = orden; }
    
    public BigDecimal getPuntos() { return puntos; }
    public void setPuntos(BigDecimal puntos) { this.puntos = puntos; }
    
    public boolean isObligatoria() { return obligatoria; }
    public void setObligatoria(boolean obligatoria) { this.obligatoria = obligatoria; }
}

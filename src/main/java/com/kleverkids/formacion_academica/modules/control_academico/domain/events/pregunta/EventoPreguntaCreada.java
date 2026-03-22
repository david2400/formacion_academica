package com.kleverkids.formacion_academica.modules.control_academico.domain.events.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.TipoPregunta;
import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;



public class EventoPreguntaCreada extends DomainEvent {
    
    private final Long preguntaId;
    private final TipoPregunta tipoPregunta;
    private final Long temaId;
    
    public EventoPreguntaCreada(Long preguntaId, TipoPregunta tipoPregunta, Long temaId) {
        super();
        this.preguntaId = preguntaId;
        this.tipoPregunta = tipoPregunta;
        this.temaId = temaId;
    }
    
    public Long getPreguntaId() {
        return preguntaId;
    }
    
    public TipoPregunta getTipoPregunta() {
        return tipoPregunta;
    }
    
    public Long getTemaId() {
        return temaId;
    }
    
    @Override
    public String getEventType() {
        return "pregunta.creada";
    }
}

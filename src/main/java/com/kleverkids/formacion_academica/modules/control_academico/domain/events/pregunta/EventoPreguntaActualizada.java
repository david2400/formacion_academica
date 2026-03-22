package com.kleverkids.formacion_academica.modules.control_academico.domain.events.pregunta;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;



public class EventoPreguntaActualizada extends DomainEvent {
    
    private final Long preguntaId;
    
    public EventoPreguntaActualizada(Long preguntaId) {
        super();
        this.preguntaId = preguntaId;
    }
    
    public Long getPreguntaId() {
        return preguntaId;
    }
    
    @Override
    public String getEventType() {
        return "pregunta.actualizada";
    }
}

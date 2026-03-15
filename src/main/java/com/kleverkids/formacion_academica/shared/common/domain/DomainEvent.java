package com.kleverkids.formacion_academica.shared.common.domain;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;


public abstract class DomainEvent {
    
    private final Long eventId;
    private final Instant occurredOn;
    
    protected DomainEvent() {
        this.eventId = ThreadLocalRandom.current().nextLong();
        this.occurredOn = Instant.now();
    }
    
    public Long getEventId() {
        return eventId;
    }
    
    public Instant getOccurredOn() {
        return occurredOn;
    }
    
    public abstract String getEventType();
}

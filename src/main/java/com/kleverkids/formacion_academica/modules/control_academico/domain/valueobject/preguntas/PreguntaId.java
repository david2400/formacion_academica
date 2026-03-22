package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import java.util.Objects;

public record PreguntaId(Long value) {
    
    public PreguntaId {
        Objects.requireNonNull(value, "Pregunta ID no puede ser nulo");
        if (value <= 0) {
            throw new IllegalArgumentException("Pregunta ID debe ser positivo");
        }
    }
    
    public static PreguntaId of(Long value) {
        return new PreguntaId(value);
    }
    
    public static PreguntaId generate() {
        return new PreguntaId(System.currentTimeMillis());
    }
    
    public String asString() {
        return value.toString();
    }
    
    public boolean isNew() {
        return value == null || value == 0;
    }
}

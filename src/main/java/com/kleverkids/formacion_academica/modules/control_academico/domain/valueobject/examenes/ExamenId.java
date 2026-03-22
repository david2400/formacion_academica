package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes;

import java.util.Objects;

public record ExamenId(Long value) {
    
    public ExamenId {
        Objects.requireNonNull(value, "Examen ID no puede ser nulo");
        if (value <= 0) {
            throw new IllegalArgumentException("Examen ID debe ser positivo");
        }
    }
    
    public static ExamenId of(Long value) {
        return new ExamenId(value);
    }
    
    public static ExamenId generate() {
        return new ExamenId(System.currentTimeMillis());
    }
    
    public String asString() {
        return value.toString();
    }
    
    public boolean isNew() {
        return value == null || value == 0;
    }
}

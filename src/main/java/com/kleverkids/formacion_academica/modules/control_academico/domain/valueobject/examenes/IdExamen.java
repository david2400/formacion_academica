package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public final class IdExamen extends ValueObject {
    
    private final Long value;
    
    private IdExamen(Long value) {
        this.value = Objects.requireNonNull(value, "IdExamen no puede ser nulo");
    }
    
    public static IdExamen of(Long value) {
        return new IdExamen(value);
    }
    
    public static IdExamen of(String value) {
        return new IdExamen(Long.parseLong(value));
    }
    
    public static IdExamen generate() {
        return new IdExamen(ThreadLocalRandom.current().nextLong());
    }
    
    public Long getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdExamen idExamen = (IdExamen) o;
        return Objects.equals(value, idExamen.value);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(value);
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}

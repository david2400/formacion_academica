package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public final class IdPregunta extends ValueObject {
    
    private final Long value;
    
    private IdPregunta(Long value) {
        this.value = Objects.requireNonNull(value, "IdPregunta no puede ser nulo");
    }
    
    public static IdPregunta of(Long value) {
        return new IdPregunta(value);
    }
    
    public static IdPregunta of(String value) {
        return new IdPregunta(Long.parseLong(value));
    }
    
    public static IdPregunta generate() {
        return new IdPregunta(ThreadLocalRandom.current().nextLong());
    }
    
    public Long getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IdPregunta that = (IdPregunta) o;
        return Objects.equals(value, that.value);
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

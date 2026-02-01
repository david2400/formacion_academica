package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.Objects;
import java.util.UUID;

public final class QuestionId extends ValueObject {
    
    private final UUID value;
    
    private QuestionId(UUID value) {
        this.value = Objects.requireNonNull(value, "QuestionId cannot be null");
    }
    
    public static QuestionId of(UUID value) {
        return new QuestionId(value);
    }
    
    public static QuestionId of(String value) {
        return new QuestionId(UUID.fromString(value));
    }
    
    public static QuestionId generate() {
        return new QuestionId(UUID.randomUUID());
    }
    
    public UUID getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        QuestionId that = (QuestionId) o;
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

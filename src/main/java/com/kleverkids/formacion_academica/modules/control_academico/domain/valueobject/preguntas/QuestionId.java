package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public final class QuestionId extends ValueObject {
    
    private final Long value;
    
    private QuestionId(Long value) {
        this.value = Objects.requireNonNull(value, "QuestionId cannot be null");
    }
    
    public static QuestionId of(Long value) {
        return new QuestionId(value);
    }
    
    public static QuestionId of(String value) {
        return new QuestionId(Long.parseLong(value));
    }
    
    public static QuestionId generate() {
        return new QuestionId(ThreadLocalRandom.current().nextLong());
    }
    
    public Long getValue() {
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

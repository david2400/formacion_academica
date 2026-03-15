package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes;

import com.kleverkids.formacion_academica.shared.common.domain.ValueObject;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;


public final class ExamId extends ValueObject {
    
    private final Long value;
    
    private ExamId(Long value) {
        this.value = Objects.requireNonNull(value, "ExamId cannot be null");
    }
    
    public static ExamId of(Long value) {
        return new ExamId(value);
    }
    
    public static ExamId of(String value) {
        return new ExamId(Long.parseLong(value));
    }
    
    public static ExamId generate() {
        return new ExamId(ThreadLocalRandom.current().nextLong());
    }
    
    public Long getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExamId examId = (ExamId) o;
        return Objects.equals(value, examId.value);
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

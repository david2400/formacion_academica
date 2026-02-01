package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ExamStatus {
    DRAFT("draft"),
    SCHEDULED("scheduled"),
    ACTIVE("active"),
    COMPLETED("completed"),
    ARCHIVED("archived");
    
    private final String value;
    
    ExamStatus(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    public static ExamStatus fromValue(String value) {
        for (ExamStatus status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Estado de examen desconocido: " + value);
    }
    
    public boolean canBeModified() {
        return this == DRAFT || this == SCHEDULED;
    }
    
    public boolean canStart() {
        return this == SCHEDULED;
    }
    
    public boolean canSubmit() {
        return this == ACTIVE;
    }
}

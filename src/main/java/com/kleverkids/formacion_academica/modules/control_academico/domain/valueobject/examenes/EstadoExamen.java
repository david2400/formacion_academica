package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes;

public enum EstadoExamen {
    BORRADOR("borrador"),
    PROGRAMADO("programado"),
    EN_PROGRESO("en_progreso"),
    FINALIZADO("finalizado"),
    CANCELADO("cancelado"),
    CALIFICADO("calificado");
    
    private final String value;
    
    EstadoExamen(String value) {
        this.value = value;
    }
    
    public String getValue() {
        return value;
    }
    
    public static EstadoExamen fromValue(String value) {
        for (EstadoExamen status : values()) {
            if (status.value.equals(value)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Estado de examen no válido: " + value);
    }
    
    public boolean isActive() {
        return this == PROGRAMADO || this == EN_PROGRESO;
    }
    
    public boolean isFinalized() {
        return this == FINALIZADO || this == CALIFICADO || this == CANCELADO;
    }
    
    public boolean canBeModified() {
        return this == BORRADOR || this == PROGRAMADO;
    }
    
    public boolean canBeGraded() {
        return this == FINALIZADO;
    }
}

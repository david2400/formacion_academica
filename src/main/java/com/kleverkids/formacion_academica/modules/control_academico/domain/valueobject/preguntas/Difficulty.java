package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.fasterxml.jackson.annotation.JsonValue;

public enum Difficulty {
    INTRO("intro"),
    BASIC("basic"),
    INTERMEDIATE("intermediate"),
    ADVANCED("advanced");
    
    private final String value;
    
    Difficulty(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    public static Difficulty fromValue(String value) {
        for (Difficulty difficulty : values()) {
            if (difficulty.value.equals(value)) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Unknown difficulty: " + value);
    }
}

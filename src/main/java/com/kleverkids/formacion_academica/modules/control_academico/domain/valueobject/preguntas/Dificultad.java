package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

public enum Dificultad {
    BASICO("basico", 1),
    INTERMEDIO("intermedio", 2),
    AVANZADO("avanzado", 3),
    EXPERTO("experto", 4);
    
    private final String value;
    private final int level;
    
    Dificultad(String value, int level) {
        this.value = value;
        this.level = level;
    }
    
    public String getValue() {
        return value;
    }
    
    public int getLevel() {
        return level;
    }
    
    public static Dificultad fromValue(String value) {
        for (Dificultad difficulty : values()) {
            if (difficulty.value.equals(value)) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Dificultad no válida: " + value);
    }
    
    public static Dificultad fromLevel(int level) {
        for (Dificultad difficulty : values()) {
            if (difficulty.level == level) {
                return difficulty;
            }
        }
        throw new IllegalArgumentException("Nivel de dificultad no válido: " + level);
    }
    
    public boolean isHigherThan(Dificultad other) {
        return this.level > other.level;
    }
    
    public boolean isLowerThan(Dificultad other) {
        return this.level < other.level;
    }
}

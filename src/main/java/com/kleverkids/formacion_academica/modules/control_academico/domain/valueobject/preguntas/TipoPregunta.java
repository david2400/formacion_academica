package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas;

import com.fasterxml.jackson.annotation.JsonValue;

public enum TipoPregunta {
    OPCION_MULTIPLE_UNICA("opcion_multiple_unica"),
    OPCION_MULTIPLE("opcion_multiple"),
    VERDADERO_FALSO("verdadero_falso"),
    ABIERTA_CORTA("abierta_corta"),
    ABIERTA_LARGA("abierta_larga"),
    NUMERICA("numerica"),
    ESCALA("escala"),
    ORDENAMIENTO("ordenamiento"),
    EMPAREJAMIENTO("emparejamiento");
    
    private final String value;
    
    TipoPregunta(String value) {
        this.value = value;
    }
    
    @JsonValue
    public String getValue() {
        return value;
    }
    
    public static TipoPregunta fromValue(String value) {
        for (TipoPregunta type : values()) {
            if (type.value.equals(value)) {
                return type;
            }
        }
        throw new IllegalArgumentException("Tipo de pregunta desconocido: " + value);
    }
    
    public boolean isMultipleChoice() {
        return this == OPCION_MULTIPLE_UNICA || this == OPCION_MULTIPLE;
    }
    
    public boolean isOpenEnded() {
        return this == ABIERTA_CORTA || this == ABIERTA_LARGA;
    }
    
    public boolean requiresExactAnswer() {
        return this == VERDADERO_FALSO || this == NUMERICA || this == ORDENAMIENTO || this == EMPAREJAMIENTO;
    }
}

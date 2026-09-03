package com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.clase;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

/**
 * Estado de seguimiento de una clase: permite distinguir en el calendario las
 * clases que aún no ocurren, las que efectivamente se dictaron y las canceladas.
 */
public enum EstadoClase {
    PROGRAMADA("programada"),
    DICTADA("dictada"),
    CANCELADA("cancelada");

    private final String value;

    EstadoClase(String value) {
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    @JsonCreator
    public static EstadoClase fromValue(String value) {
        if (value == null || value.isBlank()) {
            return PROGRAMADA;
        }
        for (EstadoClase estado : values()) {
            if (estado.value.equalsIgnoreCase(value) || estado.name().equalsIgnoreCase(value)) {
                return estado;
            }
        }
        throw new IllegalArgumentException("Estado de clase desconocido: " + value);
    }
}

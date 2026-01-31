package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;

public record ReglaCalificacionDto(String criterio,
                                   BigDecimal ponderacion,
                                   BigDecimal puntajeMaximo) {

    public ReglaCalificacionDto {
        if (criterio == null || criterio.isBlank()) {
            throw new IllegalArgumentException("El criterio es obligatorio");
        }
        if (ponderacion == null || BigDecimal.ZERO.compareTo(ponderacion) >= 0) {
            throw new IllegalArgumentException("La ponderación debe ser mayor a cero");
        }
        if (puntajeMaximo == null || BigDecimal.ZERO.compareTo(puntajeMaximo) >= 0) {
            throw new IllegalArgumentException("El puntaje máximo debe ser mayor a cero");
        }
    }
}

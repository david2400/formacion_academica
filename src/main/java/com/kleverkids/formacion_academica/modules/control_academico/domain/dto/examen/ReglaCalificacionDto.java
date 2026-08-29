package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
public class ReglaCalificacionDto {
    private String criterio;
    private BigDecimal ponderacion;
    private BigDecimal puntajeMaximo;

public ReglaCalificacionDto(String criterio, BigDecimal ponderacion, BigDecimal puntajeMaximo) {
        if (criterio == null || criterio.isBlank()) {
            throw new IllegalArgumentException("El criterio es obligatorio");
        }
        if (ponderacion == null || BigDecimal.ZERO.compareTo(ponderacion) >= 0) {
            throw new IllegalArgumentException("La ponderación debe ser mayor a cero");
        }
        if (puntajeMaximo == null || BigDecimal.ZERO.compareTo(puntajeMaximo) >= 0) {
            throw new IllegalArgumentException("El puntaje máximo debe ser mayor a cero");
        }
        this.criterio = criterio;
        this.ponderacion = ponderacion;
        this.puntajeMaximo = puntajeMaximo;
    }

}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;

@Data
@NoArgsConstructor
public class FinalizarIntentoExamenDto {
    private Long intentoId;
    private Integer puntajeTotal;

    public FinalizarIntentoExamenDto(Long intentoId, Integer puntajeTotal) {
        if (intentoId == null) {
            throw new IllegalArgumentException("El intento es obligatorio");
        }
        if (puntajeTotal != null && puntajeTotal < 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
        this.intentoId = intentoId;
        this.puntajeTotal = puntajeTotal;
    }

}

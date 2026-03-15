package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;



public record FinalizarIntentoExamenDto(Long intentoId,
                                        Integer puntajeTotal) {

    public FinalizarIntentoExamenDto {
        if (intentoId == null) {
            throw new IllegalArgumentException("El intento es obligatorio");
        }
        if (puntajeTotal != null && puntajeTotal < 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}

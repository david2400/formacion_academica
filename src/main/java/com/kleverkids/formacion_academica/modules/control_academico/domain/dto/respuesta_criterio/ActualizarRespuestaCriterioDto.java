package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio;

import java.math.BigDecimal;
import java.util.UUID;

public record ActualizarRespuestaCriterioDto(UUID id,
                                             String respuesta,
                                             BigDecimal puntajeObtenido) {

    public ActualizarRespuestaCriterioDto {
        if (id == null) {
            throw new IllegalArgumentException("El identificador de la respuesta es obligatorio");
        }
        if ((respuesta == null || respuesta.isBlank()) && puntajeObtenido == null) {
            throw new IllegalArgumentException("Debe enviar al menos un cambio (respuesta o puntaje)");
        }
        if (puntajeObtenido != null && BigDecimal.ZERO.compareTo(puntajeObtenido) > 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}

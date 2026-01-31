package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion;

import java.math.BigDecimal;
import java.util.UUID;

public record ActualizarObservacionCriterioDto(UUID id,
                                               UUID examenId,
                                               UUID criterioId,
                                               UUID estudianteId,
                                               BigDecimal puntaje,
                                               String observacion,
                                               String recomendacion) {

    public ActualizarObservacionCriterioDto {
        if (id == null) {
            throw new IllegalArgumentException("El identificador de la observación es obligatorio");
        }
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (criterioId == null) {
            throw new IllegalArgumentException("El criterio es obligatorio");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (puntaje != null && BigDecimal.ZERO.compareTo(puntaje) > 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}

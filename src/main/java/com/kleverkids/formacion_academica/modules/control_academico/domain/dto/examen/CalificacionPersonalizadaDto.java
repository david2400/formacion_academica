package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;
import java.util.UUID;

public record CalificacionPersonalizadaDto(UUID examenId,
                                           UUID estudianteId,
                                           String criterio,
                                           BigDecimal puntajeOtorgado) {

    public CalificacionPersonalizadaDto {
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (criterio == null || criterio.isBlank()) {
            throw new IllegalArgumentException("El criterio es obligatorio");
        }
        if (puntajeOtorgado == null || BigDecimal.ZERO.compareTo(puntajeOtorgado) > 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}

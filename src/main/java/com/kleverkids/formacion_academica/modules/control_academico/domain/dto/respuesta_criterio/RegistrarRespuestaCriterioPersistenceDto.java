package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio;

import java.math.BigDecimal;
import java.util.UUID;

public record RegistrarRespuestaCriterioPersistenceDto(UUID estudianteExamenId,
                                                       UUID examenId,
                                                       UUID criterioId,
                                                       UUID estudianteId,
                                                       String respuesta,
                                                       BigDecimal puntajeObtenido) {

    public RegistrarRespuestaCriterioPersistenceDto {
        if (estudianteExamenId == null) {
            throw new IllegalArgumentException("La relación estudiante-examen es obligatoria");
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
        if (respuesta == null || respuesta.isBlank()) {
            throw new IllegalArgumentException("La respuesta del criterio es obligatoria");
        }
        if (puntajeObtenido != null && BigDecimal.ZERO.compareTo(puntajeObtenido) > 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}

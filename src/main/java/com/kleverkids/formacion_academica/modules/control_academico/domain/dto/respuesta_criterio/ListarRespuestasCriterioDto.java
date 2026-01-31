package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio;

import java.util.UUID;

public record ListarRespuestasCriterioDto(UUID examenId,
                                          UUID estudianteId) {

    public ListarRespuestasCriterioDto {
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
    }
}

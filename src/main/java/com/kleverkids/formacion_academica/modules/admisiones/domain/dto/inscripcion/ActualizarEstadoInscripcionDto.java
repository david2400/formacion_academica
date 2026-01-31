package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

import java.util.UUID;

public record ActualizarEstadoInscripcionDto(UUID inscripcionId,
                                             String nuevoEstado,
                                             String motivo) {

    public ActualizarEstadoInscripcionDto {
        if (inscripcionId == null) {
            throw new IllegalArgumentException("La inscripción es obligatoria");
        }
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException("El estado es obligatorio");
        }
    }
}

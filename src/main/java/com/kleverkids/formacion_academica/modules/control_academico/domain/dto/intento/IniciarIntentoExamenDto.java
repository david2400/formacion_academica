package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.intento;

import java.util.UUID;

public record IniciarIntentoExamenDto(UUID examenId,
                                      UUID estudianteId) {

    public IniciarIntentoExamenDto {
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
    }
}

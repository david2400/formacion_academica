package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen;

import java.util.UUID;

public record RegistrarEstudianteExamenDto(UUID examenId,
                                           UUID estudianteId) {

    public RegistrarEstudianteExamenDto {
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
    }
}

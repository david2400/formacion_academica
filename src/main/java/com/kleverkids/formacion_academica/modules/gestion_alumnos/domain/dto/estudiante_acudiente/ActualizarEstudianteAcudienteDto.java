package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente;

import java.util.UUID;

public record ActualizarEstudianteAcudienteDto(UUID relacionId,String parentesco, Boolean esPrincipal, String estado) {

    public ActualizarEstudianteAcudienteDto {
        if (relacionId == null) {
            throw new IllegalArgumentException("El identificador de la relación es obligatorio");
        }
    }
}

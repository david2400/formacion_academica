package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

import java.time.LocalDate;
import java.util.UUID;

public record CrearInscripcionDto(UUID estudianteId,
                                  String periodoAcademico,
                                  LocalDate fechaSolicitud,
                                  String observaciones) {

    public CrearInscripcionDto {
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (periodoAcademico == null || periodoAcademico.isBlank()) {
            throw new IllegalArgumentException("El periodo académico es obligatorio");
        }
        if (fechaSolicitud == null) {
            throw new IllegalArgumentException("La fecha de solicitud es obligatoria");
        }
    }
}

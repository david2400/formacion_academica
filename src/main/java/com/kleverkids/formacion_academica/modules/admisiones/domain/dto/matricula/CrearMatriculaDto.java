package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula;

import java.time.LocalDate;
import java.util.UUID;

public record CrearMatriculaDto(UUID inscripcionId,
                                UUID estudianteId,
                                UUID gradoId,
                                UUID grupoId,
                                LocalDate fechaMatricula,
                                boolean renovacion) {

    public CrearMatriculaDto {
        if (inscripcionId == null) {
            throw new IllegalArgumentException("La inscripción es obligatoria");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (fechaMatricula == null) {
            throw new IllegalArgumentException("La fecha de matrícula es obligatoria");
        }
    }
}

package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo;

import java.time.LocalDate;
import java.util.UUID;

public record AsignarEstudianteGrupoDto(UUID estudianteId,
                                        UUID grupoId,
                                        LocalDate fechaAsignacion) {

    public AsignarEstudianteGrupoDto {
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (grupoId == null) {
            throw new IllegalArgumentException("El grupo es obligatorio");
        }
    }
}

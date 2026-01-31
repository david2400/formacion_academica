package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo;

import java.util.UUID;

public record CambiarEstadoEstudianteGrupoDto(UUID asignacionId,
                                              String nuevoEstado) {

    public CambiarEstadoEstudianteGrupoDto {
        if (asignacionId == null) {
            throw new IllegalArgumentException("La asignación es obligatoria");
        }
        if (nuevoEstado == null || nuevoEstado.isBlank()) {
            throw new IllegalArgumentException("El nuevo estado es obligatorio");
        }
    }
}

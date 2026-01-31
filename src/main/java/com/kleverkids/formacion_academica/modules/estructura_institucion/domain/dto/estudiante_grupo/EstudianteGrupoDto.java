package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo;

import java.time.LocalDate;
import java.util.UUID;

public record EstudianteGrupoDto(UUID id,
                                 UUID estudianteId,
                                 UUID grupoId,
                                 LocalDate fechaAsignacion,
                                 String estado) {
}

package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo;

import java.time.LocalDate;

public record EstudianteGrupoDto(Long id,
                                 Long estudianteId,
                                 Long grupoId,
                                 LocalDate fechaAsignacion,
                                 String estado) {
}

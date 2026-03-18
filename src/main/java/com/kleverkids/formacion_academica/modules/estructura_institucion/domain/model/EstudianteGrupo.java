package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDate;

public record EstudianteGrupo(Long id,
                              Long estudianteId,
                              Long grupoId,
                              LocalDate fechaAsignacion,
                              String estado) {
}

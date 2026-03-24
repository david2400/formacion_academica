package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDate;
import java.time.Instant;

public record EstudianteGrupo(Long id,
                              Long estudianteId,
                              Long grupoId,
                              LocalDate fechaAsignacion,
                              String estado,
                              boolean activo,
                              Integer usrCrea,
                              Integer usrMod,
                              Instant createdAt,
                              Instant updatedAt) {
}

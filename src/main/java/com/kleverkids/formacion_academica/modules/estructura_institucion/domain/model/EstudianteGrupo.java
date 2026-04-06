package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record EstudianteGrupo(Long id,
                              Long estudianteId,
                              Long grupoId,
                              LocalDate fechaAsignacion,
                              Integer estadoId,
                              boolean activo,
                              Integer usrCrea,
                              Integer usrMod,
                              LocalDateTime createdAt,
                              LocalDateTime updatedAt) {
}

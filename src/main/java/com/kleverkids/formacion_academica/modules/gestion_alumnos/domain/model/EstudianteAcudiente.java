package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model;

import java.time.Instant;
import java.time.LocalDate;

public record EstudianteAcudiente(Long id,
                                  Long estudianteId,
                                  Long acudienteId,
                                  Long parentescoId,
                                  boolean esPrincipal,
                                  String estado,
                                  LocalDate fechaVinculacion,
                                  LocalDate fechaFin,
                                  boolean activo,
                                  Integer usrCrea,
                                  Integer usrMod,
                                  Instant createdAt,
                                  Instant updatedAt) {
}

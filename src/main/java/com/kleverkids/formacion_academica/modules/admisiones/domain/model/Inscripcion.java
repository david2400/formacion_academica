package com.kleverkids.formacion_academica.modules.admisiones.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Inscripcion(Long id,
                          Long estudianteId,
                          String periodoAcademico,
                          LocalDate fechaSolicitud,
                          String estado,
                          String observaciones,
                          boolean activo,
                          Integer usrCrea,
                          Integer usrMod,
                          LocalDateTime createdAt,
                          LocalDateTime updatedAt) {
}

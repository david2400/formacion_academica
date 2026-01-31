package com.kleverkids.formacion_academica.modules.admisiones.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public record Inscripcion(UUID id,
                          UUID estudianteId,
                          String periodoAcademico,
                          LocalDate fechaSolicitud,
                          String estado,
                          String observaciones) {
}

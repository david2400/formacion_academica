package com.kleverkids.formacion_academica.modules.admisiones.domain.model;

import java.time.LocalDate;

public record Inscripcion(Long id,
                          Long estudianteId,
                          String periodoAcademico,
                          LocalDate fechaSolicitud,
                          String estado,
                          String observaciones) {
}

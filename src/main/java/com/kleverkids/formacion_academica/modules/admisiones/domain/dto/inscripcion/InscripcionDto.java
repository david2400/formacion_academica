package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

import java.time.LocalDate;
import java.util.UUID;

public record InscripcionDto(UUID id,
                             UUID estudianteId,
                             String periodoAcademico,
                             LocalDate fechaSolicitud,
                             String estado,
                             String observaciones) {
}

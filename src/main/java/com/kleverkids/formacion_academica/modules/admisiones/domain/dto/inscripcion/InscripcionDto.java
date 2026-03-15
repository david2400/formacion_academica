package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

import java.time.LocalDate;

public record InscripcionDto(Long id,
                             Long estudianteId,
                             String periodoAcademico,
                             LocalDate fechaSolicitud,
                             String estado,
                             String observaciones) {
}

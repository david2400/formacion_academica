package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen;

import java.time.LocalDate;

public record BuscarAsignacionesDto(
    Long examenId,
    Long claseId,
    String grado,
    String grupo,
    String estado,
    LocalDate fechaDesde,
    LocalDate fechaHasta,
    Boolean activas
) {
    public BuscarAsignacionesDto {
        if (activas == null) {
            activas = true;
        }
    }
}

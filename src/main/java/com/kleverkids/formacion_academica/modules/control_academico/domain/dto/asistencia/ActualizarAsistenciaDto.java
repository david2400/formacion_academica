package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import java.time.LocalDate;

public record ActualizarAsistenciaDto(
    Long asistenciaId,
    Long estudianteId,
    Long claseId,
    LocalDate fecha,
    String estado,
    String observaciones
) {
}

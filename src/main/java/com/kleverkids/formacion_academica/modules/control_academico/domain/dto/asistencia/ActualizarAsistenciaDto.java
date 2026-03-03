package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import java.time.LocalDate;
import java.util.UUID;

public record ActualizarAsistenciaDto(
    UUID asistenciaId,
    UUID estudianteId,
    UUID claseId,
    LocalDate fecha,
    String estado,
    String observaciones
) {
}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import java.time.LocalDate;
import java.util.List;


public record HistorialAsistenciaDto(Long estudianteId,
                                     Long claseId,
                                     LocalDate desde,
                                     LocalDate hasta,
                                     List<AsistenciaDto> asistencias) {
}

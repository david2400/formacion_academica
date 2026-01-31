package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import java.time.LocalDate;
import java.util.UUID;

public record HistorialAsistenciaFiltroDto(UUID estudianteId,
                                           UUID claseId,
                                           LocalDate desde,
                                           LocalDate hasta) {
}

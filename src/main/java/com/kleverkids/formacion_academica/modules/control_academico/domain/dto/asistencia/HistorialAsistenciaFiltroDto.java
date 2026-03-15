package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import java.time.LocalDate;


public record HistorialAsistenciaFiltroDto(Long estudianteId,
                                           Long claseId,
                                           LocalDate desde,
                                           LocalDate hasta) {
}

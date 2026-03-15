package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import java.time.LocalDate;
import java.util.List;

public record ClaseDto(Long id,
                       String nombre,
                       LocalDate fechaInicio,
                       LocalDate fechaFin,
                       List<Long> profesoresIds) {
}


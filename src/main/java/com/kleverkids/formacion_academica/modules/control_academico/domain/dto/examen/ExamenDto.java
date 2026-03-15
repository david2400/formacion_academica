package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.time.LocalDate;
import java.util.List;


public record ExamenDto(Long id,
                        Long claseId,
                        String nombre,
                        LocalDate fecha,
                        List<ReglaCalificacionDto> reglasCalificacion) {
}

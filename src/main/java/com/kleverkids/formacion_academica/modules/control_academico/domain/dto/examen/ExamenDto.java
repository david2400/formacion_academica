package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ExamenDto(UUID id,
                        UUID claseId,
                        String nombre,
                        LocalDate fecha,
                        List<ReglaCalificacionDto> reglasCalificacion) {
}

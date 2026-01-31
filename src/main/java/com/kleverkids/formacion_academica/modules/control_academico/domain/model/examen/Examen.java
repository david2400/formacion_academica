package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record Examen(UUID id,
                     UUID claseId,
                     String nombre,
                     LocalDate fecha,
                     List<ReglaCalificacion> reglasCalificacion) {
}

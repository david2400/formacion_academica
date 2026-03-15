package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import java.time.LocalDate;
import java.util.List;


public record Examen(Long id,
                     Long claseId,
                     String nombre,
                     LocalDate fecha,
                     List<ReglaCalificacion> reglasCalificacion) {
}

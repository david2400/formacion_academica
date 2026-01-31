package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula;

import java.time.LocalDate;
import java.util.UUID;

public record MatriculaDto(UUID id,
                           UUID inscripcionId,
                           UUID estudianteId,
                           UUID gradoId,
                           UUID grupoId,
                           LocalDate fechaMatricula,
                           boolean renovacion,
                           String estado,
                           String observaciones) {
}

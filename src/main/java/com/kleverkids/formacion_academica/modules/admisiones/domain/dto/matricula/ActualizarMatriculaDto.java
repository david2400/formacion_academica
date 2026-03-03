package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula;

import java.time.LocalDate;
import java.util.UUID;

public record ActualizarMatriculaDto(
    UUID matriculaId,
    UUID estudianteId,
    UUID gradoId,
    UUID grupoId,
    LocalDate fechaMatricula,
    String estado,
    String observaciones
) {
}

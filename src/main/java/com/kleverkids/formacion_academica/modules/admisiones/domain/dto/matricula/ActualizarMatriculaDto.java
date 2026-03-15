package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula;

import java.time.LocalDate;

public record ActualizarMatriculaDto(
    Long matriculaId,
    Long estudianteId,
    Long gradoId,
    Long grupoId,
    LocalDate fechaMatricula,
    String estado,
    String observaciones
) {
}

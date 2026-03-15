package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula;

import java.time.LocalDate;

public record MatriculaDto(Long id,
                           Long inscripcionId,
                           Long estudianteId,
                           Long gradoId,
                           Long grupoId,
                           LocalDate fechaMatricula,
                           boolean renovacion,
                           String estado,
                           String observaciones) {
}

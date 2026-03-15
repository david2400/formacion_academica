package com.kleverkids.formacion_academica.modules.admisiones.domain.model;

import java.time.LocalDate;

public record Matricula(Long id,
                        Long inscripcionId,
                        Long estudianteId,
                        Long gradoId,
                        Long grupoId,
                        LocalDate fechaMatricula,
                        boolean renovacion,
                        String estado,
                        String observaciones) {
}

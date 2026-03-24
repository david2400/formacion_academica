package com.kleverkids.formacion_academica.modules.admisiones.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Matricula(Long id,
                        Long inscripcionId,
                        Long estudianteId,
                        Long gradoId,
                        Long grupoId,
                        LocalDate fechaMatricula,
                        boolean renovacion,
                        String estado,
                        String observaciones,
                        boolean activo,
                        Integer usrCrea,
                        Integer usrMod,
                        LocalDateTime createdAt,
                        LocalDateTime updatedAt) {
}

package com.kleverkids.formacion_academica.modules.admisiones.domain.model;

import java.time.LocalDate;
import java.util.UUID;

public record Matricula(UUID id,
                        UUID inscripcionId,
                        UUID estudianteId,
                        UUID gradoId,
                        UUID grupoId,
                        LocalDate fechaMatricula,
                        boolean renovacion,
                        String estado,
                        String observaciones) {
}

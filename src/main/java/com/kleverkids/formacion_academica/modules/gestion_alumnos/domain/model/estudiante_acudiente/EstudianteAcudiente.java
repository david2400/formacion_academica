package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.estudiante_acudiente;

import java.time.LocalDate;
import java.util.UUID;

public record EstudianteAcudiente(UUID id,
                                          UUID estudianteId,
                                          UUID acudienteId,
                                          String parentesco,
                                          boolean esPrincipal,
                                          String estado,
                                          LocalDate fechaVinculacion,
                                          LocalDate fechaFin) {
}

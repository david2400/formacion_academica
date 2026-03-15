package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.model.estudiante_acudiente;

import java.time.LocalDate;

public record EstudianteAcudiente(Long id,
                                          Long estudianteId,
                                          Long acudienteId,
                                          String parentesco,
                                          boolean esPrincipal,
                                          String estado,
                                          LocalDate fechaVinculacion,
                                          LocalDate fechaFin) {
}

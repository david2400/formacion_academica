package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Grupo(Long id,
                    String codigo,
                    String nombre,
                    Long gradoId,
                    Integer capacidadMaxima,
                    String periodoAcademico,
                    LocalDate fechaInicio,
                    LocalDate fechaFin,
                    Long tutorId,
                    Long aulaId,
                    boolean eliminado,
                    Integer usrCrea,
                    Integer usrMod,
                    LocalDateTime createdAt,
                    LocalDateTime updatedAt) {
}

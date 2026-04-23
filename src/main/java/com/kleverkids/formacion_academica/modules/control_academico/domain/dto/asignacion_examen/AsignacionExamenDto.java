package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

public record AsignacionExamenDto(
    Long id,
    Long examenId,
    String examenNombre,
    Long claseId,
    String claseNombre,
    String grado,
    String grupo,
    LocalDate fechaAsignacion,
    LocalDateTime fechaInicio,
    LocalDateTime fechaFin,
    Integer duracionMinutos,
    Integer intentosPermitidos,
    Boolean mostrarResultadosInmediatos,
    Boolean permitirRevision,
    String estado,
    Instant createdAt,
    Instant updatedAt
) {}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ActualizarAsignacionExamenDto(
    LocalDate fechaAsignacion,
    
    @Future(message = "La fecha de inicio debe ser futura")
    LocalDateTime fechaInicio,
    
    @Future(message = "La fecha de fin debe ser futura")
    LocalDateTime fechaFin,
    
    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    Integer duracionMinutos,
    
    @Min(value = 1, message = "Debe permitir al menos 1 intento")
    Integer intentosPermitidos,
    
    Boolean mostrarResultadosInmediatos,
    
    Boolean permitirRevision,
    
    String estado
) {}

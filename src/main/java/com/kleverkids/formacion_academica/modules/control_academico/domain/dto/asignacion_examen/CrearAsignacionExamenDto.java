package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record CrearAsignacionExamenDto(
    @NotNull(message = "El ID del examen es obligatorio")
    Long examenId,
    
    @NotNull(message = "El ID de la clase es obligatorio")
    Long claseId,
    
    @NotNull(message = "La fecha de asignación es obligatoria")
    LocalDate fechaAsignacion,
    
    @NotNull(message = "La fecha de inicio es obligatoria")
    @Future(message = "La fecha de inicio debe ser futura")
    LocalDateTime fechaInicio,
    
    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser futura")
    LocalDateTime fechaFin,
    
    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    Integer duracionMinutos,
    
    @Min(value = 1, message = "Debe permitir al menos 1 intento")
    Integer intentosPermitidos,
    
    Boolean mostrarResultadosInmediatos,
    
    Boolean permitirRevision
) {
    public CrearAsignacionExamenDto {
        if (mostrarResultadosInmediatos == null) {
            mostrarResultadosInmediatos = false;
        }
        if (permitirRevision == null) {
            permitirRevision = true;
        }
        if (intentosPermitidos == null) {
            intentosPermitidos = 1;
        }
    }
}

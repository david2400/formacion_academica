package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrearAsignacionExamenDto {
    @NotNull(message = "El ID del examen es obligatorio")
    private Long examenId;

    @NotNull(message = "El ID de la clase es obligatorio")
    private Long claseId;

    @NotNull(message = "La fecha de asignación es obligatoria")
    private LocalDate fechaAsignacion;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @Future(message = "La fecha de inicio debe ser futura")
    private LocalDateTime fechaInicio;

    @NotNull(message = "La fecha de fin es obligatoria")
    @Future(message = "La fecha de fin debe ser futura")
    private LocalDateTime fechaFin;

    @Min(value = 1, message = "La duración debe ser al menos 1 minuto")
    private Integer duracionMinutos;

    @Min(value = 1, message = "Debe permitir al menos 1 intento")
    private Integer intentosPermitidos;

    private Boolean mostrarResultadosInmediatos;

    private Boolean permitirRevision;

    public CrearAsignacionExamenDto(
        Long examenId,
        Long claseId,
        LocalDate fechaAsignacion,
        LocalDateTime fechaInicio,
        LocalDateTime fechaFin,
        Integer duracionMinutos,
        Integer intentosPermitidos,
        Boolean mostrarResultadosInmediatos,
        Boolean permitirRevision
    ) {
        if (mostrarResultadosInmediatos == null) mostrarResultadosInmediatos = false;
        if (permitirRevision == null) permitirRevision = true;
        if (intentosPermitidos == null) intentosPermitidos = 1;
        this.examenId = examenId;
        this.claseId = claseId;
        this.fechaAsignacion = fechaAsignacion;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.duracionMinutos = duracionMinutos;
        this.intentosPermitidos = intentosPermitidos;
        this.mostrarResultadosInmediatos = mostrarResultadosInmediatos;
        this.permitirRevision = permitirRevision;
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.Min;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarAsignacionExamenDto {
    private LocalDate fechaAsignacion;
    private @Future(message = "La fecha de inicio debe ser futura") LocalDateTime fechaInicio;
    private @Future(message = "La fecha de fin debe ser futura") LocalDateTime fechaFin;
    private @Min(value = 1, message = "La duración debe ser al menos 1 minuto") Integer duracionMinutos;
    private @Min(value = 1, message = "Debe permitir al menos 1 intento") Integer intentosPermitidos;
    private Boolean mostrarResultadosInmediatos;
    private Boolean permitirRevision;
    private String estado;

}

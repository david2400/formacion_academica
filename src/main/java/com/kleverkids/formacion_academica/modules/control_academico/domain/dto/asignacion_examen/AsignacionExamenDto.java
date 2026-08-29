package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asignacion_examen;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionExamenDto {
    private Long id;
    private Long examenId;
    private String examenNombre;
    private Long claseId;
    private String claseNombre;
    private String grado;
    private String grupo;
    private LocalDate fechaAsignacion;
    private LocalDateTime fechaInicio;
    private LocalDateTime fechaFin;
    private Integer duracionMinutos;
    private Integer intentosPermitidos;
    private Boolean mostrarResultadosInmediatos;
    private Boolean permitirRevision;
    private String estado;
    private Instant createdAt;
    private Instant updatedAt;

}

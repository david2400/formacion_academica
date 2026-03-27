package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearMatriculaDto {

    @NotNull(message = "La inscripción es obligatoria")
    private Long inscripcionId;

    @NotNull(message = "El estudiante es obligatorio")
    private Long estudianteId;

    @NotNull(message = "El grado es obligatorio")
    private Long gradoId;

    @NotNull(message = "El grupo es obligatorio")
    private Long grupoId;

    @NotNull(message = "La fecha de matrícula es obligatoria")
    private LocalDate fechaMatricula;

    private Boolean renovacion;
    private String estado;
    private String observaciones;
}

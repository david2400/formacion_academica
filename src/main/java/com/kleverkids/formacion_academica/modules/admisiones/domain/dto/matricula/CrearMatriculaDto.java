package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearMatriculaDto {

    @NotNull(message = "La inscripción es obligatoria")
    private Long inscripcionId;

    @NotNull(message = "El estudiante es obligatorio")
    private Long estudianteId;

    private Long gradoId;

    private Long grupoId;

    @NotNull(message = "La fecha de matrícula es obligatoria")
    private LocalDate fechaMatricula;

    private boolean renovacion;

}

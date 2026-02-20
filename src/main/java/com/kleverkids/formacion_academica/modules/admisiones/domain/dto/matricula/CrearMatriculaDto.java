package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.matricula;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearMatriculaDto {

    @NotNull(message = "La inscripción es obligatoria")
    private UUID inscripcionId;

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

    private UUID gradoId;

    private UUID grupoId;

    @NotNull(message = "La fecha de matrícula es obligatoria")
    private LocalDate fechaMatricula;

    private boolean renovacion;

}

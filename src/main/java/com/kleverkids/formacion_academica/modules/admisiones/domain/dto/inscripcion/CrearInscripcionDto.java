package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearInscripcionDto {

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

    @NotBlank(message = "El periodo académico es obligatorio")
    private String periodoAcademico;

    @NotNull(message = "La fecha de solicitud es obligatoria")
    private LocalDate fechaSolicitud;

    private String observaciones;

}

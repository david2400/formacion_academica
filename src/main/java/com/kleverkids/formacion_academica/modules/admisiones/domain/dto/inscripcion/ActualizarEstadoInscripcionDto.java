package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

import jakarta.validation.constraints.NotBlank;
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
public class ActualizarEstadoInscripcionDto extends CrearInscripcionDto {

    @NotNull(message = "La inscripción es obligatoria")
    private Long inscripcionId;

    @NotNull(message = "El estudiante es obligatorio")
    private Long estudianteId;

    @NotBlank(message = "El periodo académico es obligatorio")
    private String periodoAcademico;

    @NotNull(message = "La fecha de solicitud es obligatoria")
    private LocalDate fechaSolicitud;

    private String observaciones;

    @NotBlank(message = "El estado es obligatorio")
    private Integer estadoId;
}

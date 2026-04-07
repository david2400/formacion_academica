package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

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

}

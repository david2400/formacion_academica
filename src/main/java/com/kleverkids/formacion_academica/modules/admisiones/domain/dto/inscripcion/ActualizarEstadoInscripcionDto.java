package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoInscripcionDto extends CrearInscripcionDto {

    @NotNull(message = "La inscripción es obligatoria")
    private Long inscripcionId;

}

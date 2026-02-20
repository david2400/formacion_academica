package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoInscripcionDto extends CrearInscripcionDto {

    @NotNull(message = "La inscripción es obligatoria")
    private UUID inscripcionId;

}

package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarGradoDto extends CrearGradoDto {

    @NotNull(message = "El identificador del grado es obligatorio")
    private UUID id;

}

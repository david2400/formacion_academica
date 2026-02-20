package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.tematica;

import jakarta.validation.constraints.Min;
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
public class ActualizarTematicaDto extends CrearTematicaDto {

    @NotNull(message = "El identificador de la temática es obligatorio")
    private UUID id;

    public UUID id() {
        return id;
    }

}

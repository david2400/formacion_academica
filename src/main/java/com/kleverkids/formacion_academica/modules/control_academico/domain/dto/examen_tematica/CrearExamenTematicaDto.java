package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_tematica;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrearExamenTematicaDto {

    @NotNull(message = "El identificador del examen es obligatorio")
    private Long examenId;

    @NotNull(message = "El identificador de la temática es obligatorio")
    private Long tematicaId;

}

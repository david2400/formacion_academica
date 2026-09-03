package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OptionDto {

    private Long id;

    @NotNull(message = "El texto de la opción es obligatorio")
    private String text;

    private MediaDto media;

    /**
     * Se serializa como "is_correct" siguiendo la estrategia SNAKE_CASE global.
     * Se acepta "isCorrect" como alias por compatibilidad con clientes antiguos.
     */
    @JsonAlias("isCorrect")
    @NotNull(message = "El campo is_correct es obligatorio")
    private Boolean isCorrect;

}

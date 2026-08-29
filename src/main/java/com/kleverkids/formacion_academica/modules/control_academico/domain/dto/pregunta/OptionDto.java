package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import com.fasterxml.jackson.annotation.JsonProperty;
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

    @JsonProperty("isCorrect")
    @NotNull(message = "El campo isCorrect es obligatorio")
    private Boolean isCorrect;

}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;

public record OptionDto(
    Long id,
    
    @NotNull(message = "El texto de la opción es obligatorio")
    String text,
    
    MediaDto media,
    
    @JsonProperty("isCorrect")
    @NotNull(message = "El campo isCorrect es obligatorio")
    Boolean isCorrect
) {}

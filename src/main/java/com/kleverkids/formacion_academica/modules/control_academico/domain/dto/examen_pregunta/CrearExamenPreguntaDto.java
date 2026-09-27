package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CrearExamenPreguntaDto {

    @NotNull(message = "El identificador del examen es obligatorio")
    private Long examenId;

    @NotNull(message = "El identificador de la pregunta es obligatorio")
    private Long preguntaId;

    @NotNull(message = "El orden es obligatorio")
    @Min(value = 1, message = "El orden debe ser mayor o igual a 1")
    private Integer orden;

    @DecimalMin(value = "0.0", message = "Los puntos no pueden ser negativos")
    private BigDecimal puntos;

}

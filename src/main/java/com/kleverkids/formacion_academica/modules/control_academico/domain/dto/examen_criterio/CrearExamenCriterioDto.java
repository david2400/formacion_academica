package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CrearExamenCriterioDto {

    @NotNull(message = "El identificador del examen es obligatorio")
    private Long examenId;

    @NotNull(message = "El identificador del criterio es obligatorio")
    private Long criterioEvaluadoId;

    @NotNull(message = "La ponderación es obligatoria")
    @DecimalMin(value = "0.0", message = "La ponderación no puede ser negativa")
    private BigDecimal ponderacion;

    @NotNull(message = "El orden es obligatorio")
    @Min(value = 1, message = "El orden debe ser mayor o igual a 1")
    private Integer orden;

}

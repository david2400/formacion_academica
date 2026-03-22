package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarCriterioExamenDto extends CrearCriterioExamenDto {

    @NotNull(message = "El identificador del criterio es obligatorio")
    private Long id;
}

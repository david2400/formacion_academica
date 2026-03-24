package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;


@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarObservacionCriterioDto {

    @NotNull(message = "El examen es obligatorio")
    private Long examenId;

    @NotNull(message = "El criterio es obligatorio")
    private Long criterioExamenId;

    @NotNull(message = "El estudiante es obligatorio")
    private Long estudianteId;

    private String observacion;

    private String recomendacion;

}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CrearCriterioEvaluadosDto {

    @NotBlank(message = "El nombre del criterio es obligatorio")
    private String nombre;

    private String descripcion;

    private String recomendacionBase;

    @NotNull(message = "El orden es obligatorio")
    @Min(value = 1, message = "El orden debe ser mayor o igual a 1")
    private Integer orden;

}

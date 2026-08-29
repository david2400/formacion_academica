package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarCriterioEvaluadosDto extends CrearCriterioEvaluadosDto {

    @NotNull(message = "El identificador del criterio es obligatorio")
    private Long id;
}

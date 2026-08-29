package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarExamenCriterioDto extends CrearExamenCriterioDto {

    @NotNull(message = "El identificador de la asignación es obligatorio")
    private Long id;
}

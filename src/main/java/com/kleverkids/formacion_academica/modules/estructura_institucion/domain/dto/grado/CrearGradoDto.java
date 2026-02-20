package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grado;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearGradoDto {

    @NotBlank(message = "El nombre del grado es obligatorio")
    private String nombre;

    @NotBlank(message = "El nivel educativo es obligatorio")
    private String nivelEducativo;

    @PositiveOrZero(message = "El orden debe ser positivo")
    private Integer orden;

}

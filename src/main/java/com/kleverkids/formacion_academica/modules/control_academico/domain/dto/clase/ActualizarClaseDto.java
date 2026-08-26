package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
public class ActualizarClaseDto {

    @NotNull(message = "El id de la clase es obligatorio")
    private Long id;

    @NotBlank(message = "El nombre de la clase es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha de inicio es obligatoria")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private List<Long> profesoresIds;
}

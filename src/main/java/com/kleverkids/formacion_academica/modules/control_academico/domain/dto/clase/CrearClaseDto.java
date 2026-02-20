package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
public class CrearClaseDto {

    @NotBlank(message = "El nombre de la clase es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio debe ser igual o posterior a hoy")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private List<UUID> profesoresIds;


}

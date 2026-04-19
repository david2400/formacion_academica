package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.grupo;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearGrupoDto {

    @NotBlank(message = "El código es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre es obligatorio")
    private String nombre;

    @NotNull(message = "El grado es obligatorio")
    private Long gradoId;

    @Positive(message = "La capacidad debe ser positiva")
    private Integer capacidadMaxima;

    private String periodoAcademico;

    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private Long tutorId;
    private Long salonId;
}

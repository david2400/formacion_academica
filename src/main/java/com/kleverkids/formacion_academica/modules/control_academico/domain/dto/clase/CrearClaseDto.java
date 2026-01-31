package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.clase;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CrearClaseDto {

    @NotBlank(message = "El código de clase es obligatorio")
    private String codigo;

    @NotBlank(message = "El nombre de la clase es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha de inicio es obligatoria")
    @FutureOrPresent(message = "La fecha de inicio debe ser igual o posterior a hoy")
    private LocalDate fechaInicio;

    private LocalDate fechaFin;

    private List<UUID> profesoresIds;

    public CrearClaseDto(String codigo,
                         String nombre,
                         LocalDate fechaInicio,
                         LocalDate fechaFin,
                         List<UUID> profesoresIds) {
        this.codigo = Objects.requireNonNull(codigo, "El código de clase es obligatorio");
        if (this.codigo.isBlank()) {
            throw new IllegalArgumentException("El código de clase es obligatorio");
        }
        this.nombre = Objects.requireNonNull(nombre, "El nombre de la clase es obligatorio");
        if (this.nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre de la clase es obligatorio");
        }
        this.fechaInicio = Objects.requireNonNull(fechaInicio, "La fecha de inicio es obligatoria");
        this.fechaFin = fechaFin;
        if (this.fechaFin != null && this.fechaFin.isBefore(this.fechaInicio)) {
            throw new IllegalArgumentException("La fecha fin no puede ser anterior a la fecha inicio");
        }
        this.profesoresIds = profesoresIds;
    }
}

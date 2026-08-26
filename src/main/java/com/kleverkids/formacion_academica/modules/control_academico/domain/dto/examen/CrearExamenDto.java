package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
public class CrearExamenDto {

    // @NotNull(message = "La clase del examen es obligatoria")
    // private Long claseId;

    @NotBlank(message = "El nombre del examen es obligatorio")
    private String nombre;

    private String descripcion;
    // @NotNull(message = "La fecha del examen es obligatoria")
    // private LocalDate fecha;

    @NotEmpty(message = "Debe definir al menos una regla de calificación")
    @Valid
    private List<ReglaCalificacionDto> reglas;

    public CrearExamenDto(
            String nombre,
            String descripcion,
            List<ReglaCalificacionDto> reglasCalificacion) {
        this.nombre = Objects.requireNonNull(nombre, "El nombre del examen es obligatorio");
        if (this.nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del examen es obligatorio");
        }
        this.reglas = Objects.requireNonNull(reglasCalificacion, "Debe definir al menos una regla de calificación");
        if (this.reglas.isEmpty()) {
            throw new IllegalArgumentException("Debe definir al menos una regla de calificación");
        }
    }
}

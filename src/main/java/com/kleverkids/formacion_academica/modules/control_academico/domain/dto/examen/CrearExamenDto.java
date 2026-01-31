package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
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
public class CrearExamenDto {

    @NotNull(message = "La clase del examen es obligatoria")
    private UUID claseId;

    @NotBlank(message = "El nombre del examen es obligatorio")
    private String nombre;

    @NotNull(message = "La fecha del examen es obligatoria")
    private LocalDate fecha;

    @NotEmpty(message = "Debe definir al menos una regla de calificación")
    @Valid
    private List<ReglaCalificacionDto> reglasCalificacion;

    public CrearExamenDto(UUID claseId,
                          String nombre,
                          LocalDate fecha,
                          List<ReglaCalificacionDto> reglasCalificacion) {
        this.claseId = Objects.requireNonNull(claseId, "La clase del examen es obligatoria");
        this.nombre = Objects.requireNonNull(nombre, "El nombre del examen es obligatorio");
        if (this.nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del examen es obligatorio");
        }
        this.fecha = Objects.requireNonNull(fecha, "La fecha del examen es obligatoria");
        this.reglasCalificacion = Objects.requireNonNull(reglasCalificacion, "Debe definir al menos una regla de calificación");
        if (this.reglasCalificacion.isEmpty()) {
            throw new IllegalArgumentException("Debe definir al menos una regla de calificación");
        }
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class CrearCriterioExamenDto {

    @NotNull(message = "El examen es obligatorio")
    private UUID examenId;

    @NotBlank(message = "El nombre del criterio es obligatorio")
    private String nombre;

    private String descripcion;

    @NotNull(message = "La ponderación es obligatoria")
    @DecimalMin(value = "0.0", inclusive = false, message = "La ponderación debe ser mayor a cero")
    private BigDecimal ponderacion;

    @NotNull(message = "El orden es obligatorio")
    @Min(value = 1, message = "El orden debe ser mayor o igual a 1")
    private Integer orden;

    private String recomendacionBase;

    public CrearCriterioExamenDto(UUID examenId,
                                  String nombre,
                                  String descripcion,
                                  BigDecimal ponderacion,
                                  Integer orden,
                                  String recomendacionBase) {
        this.examenId = Objects.requireNonNull(examenId, "El examen es obligatorio");
        this.nombre = Objects.requireNonNull(nombre, "El nombre del criterio es obligatorio");
        if (this.nombre.isBlank()) {
            throw new IllegalArgumentException("El nombre del criterio es obligatorio");
        }
        this.descripcion = descripcion;
        this.ponderacion = Objects.requireNonNull(ponderacion, "La ponderación es obligatoria");
        if (BigDecimal.ZERO.compareTo(this.ponderacion) >= 0) {
            throw new IllegalArgumentException("La ponderación debe ser mayor a cero");
        }
        this.orden = Objects.requireNonNull(orden, "El orden es obligatorio");
        if (this.orden < 1) {
            throw new IllegalArgumentException("El orden debe ser mayor o igual a 1");
        }
        this.recomendacionBase = recomendacionBase;
    }
}

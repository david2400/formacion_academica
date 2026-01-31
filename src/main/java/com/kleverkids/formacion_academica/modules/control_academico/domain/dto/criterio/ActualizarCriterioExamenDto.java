package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
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
public class ActualizarCriterioExamenDto {

    @NotNull(message = "El identificador del criterio es obligatorio")
    private UUID id;

    @NotNull(message = "El examen es obligatorio")
    private UUID examenId;

    private String nombre;

    private String descripcion;

    @DecimalMin(value = "0.0", inclusive = false, message = "La ponderación debe ser mayor a cero")
    private BigDecimal ponderacion;

    @Min(value = 1, message = "El orden debe ser mayor o igual a 1")
    private Integer orden;

    private String recomendacionBase;

    public ActualizarCriterioExamenDto(UUID id,
                                       UUID examenId,
                                       String nombre,
                                       String descripcion,
                                       BigDecimal ponderacion,
                                       Integer orden,
                                       String recomendacionBase) {
        this.id = Objects.requireNonNull(id, "El identificador del criterio es obligatorio");
        this.examenId = Objects.requireNonNull(examenId, "El examen es obligatorio");
        if (ponderacion != null && BigDecimal.ZERO.compareTo(ponderacion) >= 0) {
            throw new IllegalArgumentException("La ponderación debe ser mayor a cero");
        }
        this.ponderacion = ponderacion;
        if (orden != null && orden < 1) {
            throw new IllegalArgumentException("El orden debe ser mayor o igual a 1");
        }
        this.orden = orden;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.recomendacionBase = recomendacionBase;
    }
}

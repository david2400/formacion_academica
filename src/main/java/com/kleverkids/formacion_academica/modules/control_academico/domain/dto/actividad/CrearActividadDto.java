package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.actividad;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class CrearActividadDto {

    @NotBlank(message = "El título de la actividad es obligatorio")
    private String titulo;

    private String descripcion;

    @NotBlank(message = "Las instrucciones son obligatorias")
    private String instrucciones;

    @NotNull(message = "El tipo de actividad es obligatorio")
    private String tipo; // LECTURA, VIDEO, CUESTIONARIO, etc.

    @NotNull(message = "El curso es obligatorio")
    private Long cursoId;

    private Long moduloId;

    @PositiveOrZero(message = "El orden debe ser positivo o cero")
    private Integer orden;

    private LocalDateTime fechaInicio;

    private LocalDateTime fechaFin;

    // Campos de configuración simplificados
    private Boolean obligatoria = true;

    private Integer intentosPermitidos = 1;

    private Boolean mostrarRetroalimentacion = true;

    private Boolean permiteRevisar = false;
}

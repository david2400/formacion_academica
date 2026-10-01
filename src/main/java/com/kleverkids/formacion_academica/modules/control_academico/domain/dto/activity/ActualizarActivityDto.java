package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * No permite cambiar {@code activityTypeId}: el tipo determina qué contenido
 * es válido y qué handler la valida/ejecuta; cambiarlo a mitad de edición
 * dejaría contenido huérfano. Si el docente se equivocó de tipo, la acción
 * correcta es eliminar y crear de nuevo (la actividad en DRAFT aún no tiene
 * historial que perder).
 */
@Data
@NoArgsConstructor
public class ActualizarActivityDto {

    @NotNull
    private Long id;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;
    private String instrucciones;
    private Integer duracionEstimadaMinutos;
    private BigDecimal puntos;
    private BigDecimal notaMinima;
    private Boolean esObligatoria;
    private Boolean permiteReintentos;
    private Integer maxIntentos;
}

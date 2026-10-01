package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearActivityDto {

    @NotNull(message = "El tipo de actividad es obligatorio")
    private Long activityTypeId;

    @NotBlank(message = "El título es obligatorio")
    private String titulo;

    private String descripcion;
    private String instrucciones;
    private Integer duracionEstimadaMinutos;
    private BigDecimal puntos;
    private BigDecimal notaMinima;

    /** Si no se envía, la actividad se crea como obligatoria. */
    private Boolean esObligatoria;

    /** Si no se envía, se permite reintentar. */
    private Boolean permiteReintentos;

    /** NULL = intentos ilimitados. */
    private Integer maxIntentos;
}

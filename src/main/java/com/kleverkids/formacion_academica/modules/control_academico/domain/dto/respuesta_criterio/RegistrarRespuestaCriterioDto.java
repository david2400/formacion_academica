package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class RegistrarRespuestaCriterioDto {

    @NotNull(message = "El examen es obligatorio")
    private UUID examenId;

    @NotNull(message = "El criterio es obligatorio")
    private UUID criterioId;

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

    @NotBlank(message = "La respuesta del criterio es obligatoria")
    private String respuesta;

    @DecimalMin(value = "0.0", message = "El puntaje debe ser mayor o igual a cero")
    private BigDecimal puntajeObtenido;

}

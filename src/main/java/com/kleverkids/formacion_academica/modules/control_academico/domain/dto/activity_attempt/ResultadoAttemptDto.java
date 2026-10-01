package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Resultado calculado por un {@code ActivityHandler} al completar un intento:
 * puntaje agregado (ya en la escala de puntos de la Activity) y si se
 * considera aprobado según {@code Activity.notaMinima}.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResultadoAttemptDto {

    private BigDecimal score;
    private boolean aprobado;
}

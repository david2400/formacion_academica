package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenCriterioDto {
    private Long id;
    private Long examenId;
    private Long criterioEvaluadoId;
    private BigDecimal ponderacion;
    private Integer orden;
}

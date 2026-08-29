package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EvaluationCriteriaDto {
    private Long id;
    private String name;
    private String description;
    private BigDecimal weight;
    private BigDecimal maxScore;

}

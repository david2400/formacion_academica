package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;


public record EvaluationCriteriaDto(
    Long id,
    String name,
    String description,
    BigDecimal weight,
    BigDecimal maxScore
) {}

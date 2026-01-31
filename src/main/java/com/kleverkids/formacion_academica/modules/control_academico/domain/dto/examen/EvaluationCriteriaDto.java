package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;
import java.util.UUID;

public record EvaluationCriteriaDto(
    UUID id,
    String name,
    String description,
    BigDecimal weight,
    BigDecimal maxScore
) {}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;
import java.util.UUID;

public record ExamQuestionDto(
    UUID id,
    UUID questionId,
    int order,
    BigDecimal points,
    boolean required
) {}

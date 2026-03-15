package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;


public record ExamQuestionDto(
    Long id,
    Long questionId,
    int order,
    BigDecimal points,
    boolean required
) {}

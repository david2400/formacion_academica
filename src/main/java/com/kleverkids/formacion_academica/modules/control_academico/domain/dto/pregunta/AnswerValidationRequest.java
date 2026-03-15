package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public record AnswerValidationRequest(
    // For multiple choice single
    Long selectedOptionId,
    // For multiple choice multi
    List<Long> selectedOptionIds,
    // For true/false
    Boolean booleanAnswer,
    // For open short/long
    String textAnswer,
    // For numeric
    BigDecimal numericAnswer,
    // For scale
    Integer scaleValue,
    // For ordering
    List<Long> orderedItemIds,
    // For matching
    Map<Long, Long> matchedPairs
) {}

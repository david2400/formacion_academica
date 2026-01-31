package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record AnswerValidationRequest(
    // For multiple choice single
    UUID selectedOptionId,
    // For multiple choice multi
    List<UUID> selectedOptionIds,
    // For true/false
    Boolean booleanAnswer,
    // For open short/long
    String textAnswer,
    // For numeric
    BigDecimal numericAnswer,
    // For scale
    Integer scaleValue,
    // For ordering
    List<UUID> orderedItemIds,
    // For matching
    Map<UUID, UUID> matchedPairs
) {}

package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record UpdateQuestionCommand(
    String questionText,
    String difficulty,
    Integer maxScore,
    UUID themeId,
    List<MediaDto> media,
    String hint,
    String explanation,
    List<String> tags,
    Map<String, Object> metadata,
    // Multiple choice fields
    List<OptionDto> options,
    UUID correctOptionId,
    List<UUID> correctOptionIds,
    Integer minSelections,
    Integer maxSelections,
    // True/False field
    Boolean correctAnswer,
    // Open short fields
    List<String> acceptedAnswers,
    Boolean caseSensitive,
    Integer maxLength,
    // Open long fields
    RubricDto rubric,
    Integer minWords,
    Integer maxWords,
    Boolean allowAttachments,
    // Numeric fields
    BigDecimal correctValue,
    BigDecimal tolerance,
    String unit,
    Integer decimalPlaces,
    // Scale fields
    ScaleConfigDto scaleConfig,
    Integer expectedValue,
    // Ordering fields
    List<OrderingItemDto> items,
    Boolean partialCredit,
    // Matching fields
    List<MatchingPairDto> pairs
) {}

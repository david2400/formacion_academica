package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.math.BigDecimal;

public record ValidationResult(
    boolean isCorrect,
    BigDecimal score,
    BigDecimal maxScore,
    String feedback,
    boolean requiresManualGrading
) {
    public static ValidationResult correct(BigDecimal maxScore) {
        return new ValidationResult(true, maxScore, maxScore, null, false);
    }
    
    public static ValidationResult incorrect(BigDecimal maxScore) {
        return new ValidationResult(false, BigDecimal.ZERO, maxScore, null, false);
    }
    
    public static ValidationResult partial(BigDecimal score, BigDecimal maxScore) {
        return new ValidationResult(false, score, maxScore, null, false);
    }
    
    public static ValidationResult requiresManualGrading(BigDecimal maxScore) {
        return new ValidationResult(false, BigDecimal.ZERO, maxScore, "Requires manual grading", true);
    }
    
    public ValidationResult withFeedback(String feedback) {
        return new ValidationResult(isCorrect, score, maxScore, feedback, requiresManualGrading);
    }
}

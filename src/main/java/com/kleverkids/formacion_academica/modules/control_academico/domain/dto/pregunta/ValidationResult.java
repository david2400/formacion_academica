package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ValidationResult {
    private boolean isCorrect;
    private BigDecimal score;
    private BigDecimal maxScore;
    private String feedback;
    private boolean requiresManualGrading;

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

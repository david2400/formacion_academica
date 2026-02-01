package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.exception.InvalidQuestionException;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.QuestionType;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class NumericQuestion extends Question {
    
    private BigDecimal correctValue;
    private BigDecimal tolerance;
    private String unit;
    private Integer decimalPlaces;
    
    public NumericQuestion() {
        super();
        this.questionType = QuestionType.NUMERIC;
    }
    
    public NumericQuestion(UUID id, String questionText, Difficulty difficulty, int maxScore,
                            UUID themeId, List<Media> media, String hint, String explanation,
                            List<String> tags, Map<String, Object> metadata,
                            BigDecimal correctValue, BigDecimal tolerance, String unit, Integer decimalPlaces) {
        super(id, questionText, QuestionType.NUMERIC, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.correctValue = correctValue;
        this.tolerance = tolerance;
        this.unit = unit;
        this.decimalPlaces = decimalPlaces;
        validate();
    }
    
    public BigDecimal getCorrectValue() {
        return correctValue;
    }
    
    public void setCorrectValue(BigDecimal correctValue) {
        this.correctValue = correctValue;
    }
    
    public BigDecimal getTolerance() {
        return tolerance;
    }
    
    public void setTolerance(BigDecimal tolerance) {
        this.tolerance = tolerance;
    }
    
    public String getUnit() {
        return unit;
    }
    
    public void setUnit(String unit) {
        this.unit = unit;
    }
    
    public Integer getDecimalPlaces() {
        return decimalPlaces;
    }
    
    public void setDecimalPlaces(Integer decimalPlaces) {
        this.decimalPlaces = decimalPlaces;
    }
    
    @Override
    public void validate() {
        if (correctValue == null) {
            throw new InvalidQuestionException("Correct value is required for numeric questions");
        }
        if (tolerance != null && tolerance.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidQuestionException("Tolerance cannot be negative");
        }
    }
}

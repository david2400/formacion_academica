package com.kleverkids.formacion_academica.modules.questions.domain.model;

import com.kleverkids.formacion_academica.modules.questions.domain.exception.InvalidQuestionException;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.Difficulty;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.Media;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.QuestionType;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.ScaleConfig;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ScaleQuestion extends Question {
    
    private ScaleConfig scaleConfig;
    private Integer expectedValue;
    
    public ScaleQuestion() {
        super();
        this.questionType = QuestionType.SCALE;
    }
    
    public ScaleQuestion(UUID id, String questionText, Difficulty difficulty, int maxScore,
                          UUID themeId, List<Media> media, String hint, String explanation,
                          List<String> tags, Map<String, Object> metadata,
                          ScaleConfig scaleConfig, Integer expectedValue) {
        super(id, questionText, QuestionType.SCALE, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.scaleConfig = scaleConfig;
        this.expectedValue = expectedValue;
        validate();
    }
    
    public ScaleConfig getScaleConfig() {
        return scaleConfig;
    }
    
    public void setScaleConfig(ScaleConfig scaleConfig) {
        this.scaleConfig = scaleConfig;
    }
    
    public Integer getExpectedValue() {
        return expectedValue;
    }
    
    public void setExpectedValue(Integer expectedValue) {
        this.expectedValue = expectedValue;
    }
    
    @Override
    public void validate() {
        if (scaleConfig == null) {
            throw new InvalidQuestionException("Scale configuration is required");
        }
    }
}

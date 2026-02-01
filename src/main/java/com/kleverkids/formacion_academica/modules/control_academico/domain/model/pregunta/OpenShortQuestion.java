package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.QuestionType;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class OpenShortQuestion extends Question {
    
    private List<String> acceptedAnswers;
    private boolean caseSensitive;
    private Integer maxLength;
    
    public OpenShortQuestion() {
        super();
        this.questionType = QuestionType.OPEN_SHORT;
    }
    
    public OpenShortQuestion(UUID id, String questionText, Difficulty difficulty, int maxScore,
                              UUID themeId, List<Media> media, String hint, String explanation,
                              List<String> tags, Map<String, Object> metadata,
                              List<String> acceptedAnswers, boolean caseSensitive, Integer maxLength) {
        super(id, questionText, QuestionType.OPEN_SHORT, difficulty, maxScore,
              themeId, media, hint, explanation, tags, metadata);
        this.acceptedAnswers = acceptedAnswers;
        this.caseSensitive = caseSensitive;
        this.maxLength = maxLength;
    }
    
    public List<String> getAcceptedAnswers() {
        return acceptedAnswers;
    }
    
    public void setAcceptedAnswers(List<String> acceptedAnswers) {
        this.acceptedAnswers = acceptedAnswers;
    }
    
    public boolean isCaseSensitive() {
        return caseSensitive;
    }
    
    public void setCaseSensitive(boolean caseSensitive) {
        this.caseSensitive = caseSensitive;
    }
    
    public Integer getMaxLength() {
        return maxLength;
    }
    
    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }
    
    @Override
    public void validate() {
        // Open short questions can have optional accepted answers for auto-grading
    }
}

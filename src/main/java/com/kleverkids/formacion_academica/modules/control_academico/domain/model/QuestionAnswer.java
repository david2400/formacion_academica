package com.kleverkids.formacion_academica.modules.control_academico.domain.model;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class QuestionAnswer {
    
    private UUID id;
    private UUID questionId;
    private Object answer; // Flexible to hold different answer types
    private BigDecimal score;
    private boolean graded;
    private String feedback;
    
    public QuestionAnswer() {}
    
    public QuestionAnswer(UUID id, UUID questionId, Object answer) {
        this.id = id != null ? id : UUID.randomUUID();
        this.questionId = questionId;
        this.answer = answer;
        this.graded = false;
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getQuestionId() { return questionId; }
    public void setQuestionId(UUID questionId) { this.questionId = questionId; }
    
    public Object getAnswer() { return answer; }
    public void setAnswer(Object answer) { this.answer = answer; }
    
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    
    public boolean isGraded() { return graded; }
    public void setGraded(boolean graded) { this.graded = graded; }
    
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
    
    public void grade(BigDecimal score, String feedback) {
        this.score = score;
        this.feedback = feedback;
        this.graded = true;
    }
    
    // Type-safe answer getters
    public UUID getSelectedOptionId() {
        return answer instanceof UUID ? (UUID) answer : null;
    }
    
    @SuppressWarnings("unchecked")
    public List<UUID> getSelectedOptionIds() {
        return answer instanceof List ? (List<UUID>) answer : null;
    }
    
    public Boolean getBooleanAnswer() {
        return answer instanceof Boolean ? (Boolean) answer : null;
    }
    
    public String getTextAnswer() {
        return answer instanceof String ? (String) answer : null;
    }
    
    public BigDecimal getNumericAnswer() {
        return answer instanceof BigDecimal ? (BigDecimal) answer : null;
    }
    
    public Integer getScaleValue() {
        return answer instanceof Integer ? (Integer) answer : null;
    }
    
    @SuppressWarnings("unchecked")
    public List<UUID> getOrderedItemIds() {
        return answer instanceof List ? (List<UUID>) answer : null;
    }
    
    @SuppressWarnings("unchecked")
    public Map<UUID, UUID> getMatchedPairs() {
        return answer instanceof Map ? (Map<UUID, UUID>) answer : null;
    }
}

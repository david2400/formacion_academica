package com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public class QuestionAnswer {
    
    private Long id;
    private Long questionId;
    private Object answer; // Flexible to hold different answer types
    private BigDecimal score;
    private boolean graded;
    private String feedback;
    
    public QuestionAnswer() {}
    
    public QuestionAnswer(Long id, Long questionId, Object answer) {
        this.id = id ;
        this.questionId = questionId;
        this.answer = answer;
        this.graded = false;
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getQuestionId() { return questionId; }
    public void setQuestionId(Long questionId) { this.questionId = questionId; }
    
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
    public Long getSelectedOptionId() {
        return answer instanceof Long ? (Long) answer : null;
    }
    
    @SuppressWarnings("unchecked")
    public List<Long> getSelectedOptionIds() {
        return answer instanceof List ? (List<Long>) answer : null;
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
    public List<Long> getOrderedItemIds() {
        return answer instanceof List ? (List<Long>) answer : null;
    }
    
    @SuppressWarnings("unchecked")
    public Map<Long, Long> getMatchedPairs() {
        return answer instanceof Map ? (Map<Long, Long>) answer : null;
    }
}

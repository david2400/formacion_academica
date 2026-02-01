package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta;

import java.math.BigDecimal;
import java.util.UUID;

public class QuestionResultEmbeddable {
    
    private UUID questionId;
    private BigDecimal score;
    private BigDecimal maxScore;
    private boolean correct;
    private String feedback;
    
    public QuestionResultEmbeddable() {}
    
    public QuestionResultEmbeddable(UUID questionId, BigDecimal score, BigDecimal maxScore, boolean correct, String feedback) {
        this.questionId = questionId;
        this.score = score;
        this.maxScore = maxScore;
        this.correct = correct;
        this.feedback = feedback;
    }
    
    public UUID getQuestionId() { return questionId; }
    public void setQuestionId(UUID questionId) { this.questionId = questionId; }
    
    public BigDecimal getScore() { return score; }
    public void setScore(BigDecimal score) { this.score = score; }
    
    public BigDecimal getMaxScore() { return maxScore; }
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }
    
    public boolean isCorrect() { return correct; }
    public void setCorrect(boolean correct) { this.correct = correct; }
    
    public String getFeedback() { return feedback; }
    public void setFeedback(String feedback) { this.feedback = feedback; }
}

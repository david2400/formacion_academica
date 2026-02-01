package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class QuestionAnswerEmbeddable {
    
    private UUID id;
    private UUID questionId;
    private Object answer;
    private BigDecimal score;
    private boolean graded;
    private String feedback;
    
    public QuestionAnswerEmbeddable() {}
    
    public QuestionAnswerEmbeddable(UUID id, UUID questionId, Object answer, BigDecimal score, boolean graded, String feedback) {
        this.id = id;
        this.questionId = questionId;
        this.answer = answer;
        this.score = score;
        this.graded = graded;
        this.feedback = feedback;
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
}

package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class ExamResult {
    
    private UUID id;
    private UUID examId;
    private UUID studentId;
    private UUID submissionId;
    private BigDecimal totalScore;
    private BigDecimal maxScore;
    private BigDecimal percentage;
    private String grade;
    private List<QuestionResult> questionResults;
    private Instant gradedAt;
    private UUID gradedBy;
    
    public ExamResult() {}
    
    public ExamResult(UUID id, UUID examId, UUID studentId, UUID submissionId,
                      BigDecimal totalScore, BigDecimal maxScore, List<QuestionResult> questionResults) {
        this.id = id != null ? id : UUID.randomUUID();
        this.examId = examId;
        this.studentId = studentId;
        this.submissionId = submissionId;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
        this.questionResults = questionResults;
        this.gradedAt = Instant.now();
        calculatePercentage();
    }
    
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public UUID getExamId() { return examId; }
    public void setExamId(UUID examId) { this.examId = examId; }
    
    public UUID getStudentId() { return studentId; }
    public void setStudentId(UUID studentId) { this.studentId = studentId; }
    
    public UUID getSubmissionId() { return submissionId; }
    public void setSubmissionId(UUID submissionId) { this.submissionId = submissionId; }
    
    public BigDecimal getTotalScore() { return totalScore; }
    public void setTotalScore(BigDecimal totalScore) { 
        this.totalScore = totalScore; 
        calculatePercentage();
    }
    
    public BigDecimal getMaxScore() { return maxScore; }
    public void setMaxScore(BigDecimal maxScore) { 
        this.maxScore = maxScore; 
        calculatePercentage();
    }
    
    public BigDecimal getPercentage() { return percentage; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    
    public List<QuestionResult> getQuestionResults() { return questionResults; }
    public void setQuestionResults(List<QuestionResult> questionResults) { this.questionResults = questionResults; }
    
    public Instant getGradedAt() { return gradedAt; }
    public void setGradedAt(Instant gradedAt) { this.gradedAt = gradedAt; }
    
    public UUID getGradedBy() { return gradedBy; }
    public void setGradedBy(UUID gradedBy) { this.gradedBy = gradedBy; }
    
    private void calculatePercentage() {
        if (totalScore != null && maxScore != null && maxScore.compareTo(BigDecimal.ZERO) > 0) {
            this.percentage = totalScore
                .multiply(BigDecimal.valueOf(100))
                .divide(maxScore, 2, RoundingMode.HALF_UP);
        }
    }
    
    public static class QuestionResult {
        private UUID questionId;
        private BigDecimal score;
        private BigDecimal maxScore;
        private boolean correct;
        private String feedback;
        
        public QuestionResult() {}
        
        public QuestionResult(UUID questionId, BigDecimal score, BigDecimal maxScore, boolean correct, String feedback) {
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
}

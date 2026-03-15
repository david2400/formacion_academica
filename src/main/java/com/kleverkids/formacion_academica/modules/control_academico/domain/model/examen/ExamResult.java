package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Instant;
import java.util.List;


public class ExamResult {
    
    private Long id;
    private Long examId;
    private Long studentId;
    private Long submissionId;
    private BigDecimal totalScore;
    private BigDecimal maxScore;
    private BigDecimal percentage;
    private String grade;
    private List<QuestionResult> questionResults;
    private Instant gradedAt;
    private Long gradedBy;
    
    public ExamResult() {}
    
    public ExamResult(Long id, Long examId, Long studentId, Long submissionId,
                      BigDecimal totalScore, BigDecimal maxScore, List<QuestionResult> questionResults) {
        this.id = id ;
        this.examId = examId;
        this.studentId = studentId;
        this.submissionId = submissionId;
        this.totalScore = totalScore;
        this.maxScore = maxScore;
        this.questionResults = questionResults;
        this.gradedAt = Instant.now();
        calculatePercentage();
    }
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getExamId() { return examId; }
    public void setExamId(Long examId) { this.examId = examId; }
    
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    
    public Long getSubmissionId() { return submissionId; }
    public void setSubmissionId(Long submissionId) { this.submissionId = submissionId; }
    
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
    
    public Long getGradedBy() { return gradedBy; }
    public void setGradedBy(Long gradedBy) { this.gradedBy = gradedBy; }
    
    private void calculatePercentage() {
        if (totalScore != null && maxScore != null && maxScore.compareTo(BigDecimal.ZERO) > 0) {
            this.percentage = totalScore
                .multiply(BigDecimal.valueOf(100))
                .divide(maxScore, 2, RoundingMode.HALF_UP);
        }
    }
    
    public static class QuestionResult {
        private Long questionId;
        private BigDecimal score;
        private BigDecimal maxScore;
        private boolean correct;
        private String feedback;
        
        public QuestionResult() {}
        
        public QuestionResult(Long questionId, BigDecimal score, BigDecimal maxScore, boolean correct, String feedback) {
            this.questionId = questionId;
            this.score = score;
            this.maxScore = maxScore;
            this.correct = correct;
            this.feedback = feedback;
        }
        
        public Long getQuestionId() { return questionId; }
        public void setQuestionId(Long questionId) { this.questionId = questionId; }
        
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

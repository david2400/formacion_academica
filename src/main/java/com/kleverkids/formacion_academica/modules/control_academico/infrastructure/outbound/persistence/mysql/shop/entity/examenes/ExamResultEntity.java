package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.QuestionResultEmbeddable;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "exam_results")
public class ExamResultEntity {
    
    @Id
    private Long id;
    
    @Column(name = "exam_id", nullable = false)
    private Long examId;
    
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "submission_id", nullable = false)
    private Long submissionId;
    
    @Column(name = "total_score", precision = 10, scale = 2)
    private BigDecimal totalScore;
    
    @Column(name = "max_score", precision = 10, scale = 2)
    private BigDecimal maxScore;
    
    @Column(name = "percentage", precision = 5, scale = 2)
    private BigDecimal percentage;
    
    private String grade;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "question_results", columnDefinition = "json")
    private List<QuestionResultEmbeddable> questionResults;
    
    @Column(name = "graded_at")
    private Instant gradedAt;
    
    @Column(name = "graded_by")
    private Long gradedBy;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getExamId() { return examId; }
    public void setExamId(Long examId) { this.examId = examId; }
    
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    
    public Long getSubmissionId() { return submissionId; }
    public void setSubmissionId(Long submissionId) { this.submissionId = submissionId; }
    
    public BigDecimal getTotalScore() { return totalScore; }
    public void setTotalScore(BigDecimal totalScore) { this.totalScore = totalScore; }
    
    public BigDecimal getMaxScore() { return maxScore; }
    public void setMaxScore(BigDecimal maxScore) { this.maxScore = maxScore; }
    
    public BigDecimal getPercentage() { return percentage; }
    public void setPercentage(BigDecimal percentage) { this.percentage = percentage; }
    
    public String getGrade() { return grade; }
    public void setGrade(String grade) { this.grade = grade; }
    
    public List<QuestionResultEmbeddable> getQuestionResults() { return questionResults; }
    public void setQuestionResults(List<QuestionResultEmbeddable> questionResults) { this.questionResults = questionResults; }
    
    public Instant getGradedAt() { return gradedAt; }
    public void setGradedAt(Instant gradedAt) { this.gradedAt = gradedAt; }
    
    public Long getGradedBy() { return gradedBy; }
    public void setGradedBy(Long gradedBy) { this.gradedBy = gradedBy; }
}

package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.examenes;

import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta.QuestionAnswerEmbeddable;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;


@Entity
@Table(name = "exam_submissions")
public class ExamSubmissionEntity {
    
    @Id
    private Long id;
    
    @Column(name = "exam_id", nullable = false)
    private Long examId;
    
    @Column(name = "student_id", nullable = false)
    private Long studentId;
    
    @Column(name = "started_at")
    private Instant startedAt;
    
    @Column(name = "submitted_at")
    private Instant submittedAt;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "answers", columnDefinition = "json")
    private List<QuestionAnswerEmbeddable> answers;
    
    @Column(nullable = false)
    private String status;
    
    @Column(name = "total_score", precision = 10, scale = 2)
    private BigDecimal totalScore;
    
    @Column(name = "graded")
    private boolean graded;
    
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Long getExamId() { return examId; }
    public void setExamId(Long examId) { this.examId = examId; }
    
    public Long getStudentId() { return studentId; }
    public void setStudentId(Long studentId) { this.studentId = studentId; }
    
    public Instant getStartedAt() { return startedAt; }
    public void setStartedAt(Instant startedAt) { this.startedAt = startedAt; }
    
    public Instant getSubmittedAt() { return submittedAt; }
    public void setSubmittedAt(Instant submittedAt) { this.submittedAt = submittedAt; }
    
    public List<QuestionAnswerEmbeddable> getAnswers() { return answers; }
    public void setAnswers(List<QuestionAnswerEmbeddable> answers) { this.answers = answers; }
    
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    
    public BigDecimal getTotalScore() { return totalScore; }
    public void setTotalScore(BigDecimal totalScore) { this.totalScore = totalScore; }
    
    public boolean isGraded() { return graded; }
    public void setGraded(boolean graded) { this.graded = graded; }
}

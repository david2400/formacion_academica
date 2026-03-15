package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.QuestionAnswer;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class ExamSubmission {
    
    private Long id;
    private Long examId;
    private Long studentId;
    private Instant startedAt;
    private Instant submittedAt;
    private List<QuestionAnswer> answers;
    private SubmissionStatus status;
    private BigDecimal totalScore;
    private boolean graded;
    
    public ExamSubmission() {
        this.answers = new ArrayList<>();
        this.status = SubmissionStatus.IN_PROGRESS;
    }
    
    public ExamSubmission(Long id, Long examId, Long studentId) {
        this.id = id ;
        this.examId = examId;
        this.studentId = studentId;
        this.startedAt = Instant.now();
        this.answers = new ArrayList<>();
        this.status = SubmissionStatus.IN_PROGRESS;
        this.graded = false;
    }
    
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
    
    public List<QuestionAnswer> getAnswers() { return answers; }
    public void setAnswers(List<QuestionAnswer> answers) { this.answers = answers; }
    
    public SubmissionStatus getStatus() { return status; }
    public void setStatus(SubmissionStatus status) { this.status = status; }
    
    public BigDecimal getTotalScore() { return totalScore; }
    public void setTotalScore(BigDecimal totalScore) { this.totalScore = totalScore; }
    
    public boolean isGraded() { return graded; }
    public void setGraded(boolean graded) { this.graded = graded; }
    
    public void addAnswer(QuestionAnswer answer) {
        this.answers.add(answer);
    }
    
    public void submit() {
        if (this.status != SubmissionStatus.IN_PROGRESS) {
            throw new IllegalStateException("No se puede entregar - no está en progreso");
        }
        this.submittedAt = Instant.now();
        this.status = SubmissionStatus.SUBMITTED;
    }
    
    public void grade(BigDecimal score) {
        if (this.status != SubmissionStatus.SUBMITTED) {
            throw new IllegalStateException("No se puede calificar - no ha sido entregado");
        }
        this.totalScore = score;
        this.graded = true;
        this.status = SubmissionStatus.GRADED;
    }
    
    public enum SubmissionStatus {
        IN_PROGRESS,
        SUBMITTED,
        GRADED
    }
}

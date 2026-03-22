package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.EvaluationCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.ExamStatus;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.examenes.TimeConfig;
import com.kleverkids.formacion_academica.shared.common.domain.AggregateRoot;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;


public class Exam extends AggregateRoot<Long> {
    
    private Long id;
    private String name;
    private String code;
    private String subject;
    private String gradeLevel;
    private String instructions;
    private ExamStatus status;
    private TimeConfig timeConfig;
    private List<PreguntaExamen> questions;
    private List<EvaluationCriteria> criteria;
    private BigDecimal totalPoints;
    private Instant createdAt;
    private Instant updatedAt;
    private boolean activo;
    
    public Exam() {
        this.questions = new ArrayList<>();
        this.criteria = new ArrayList<>();
        this.status = ExamStatus.DRAFT;
    }
    
    public Exam(Long id, String name, String code, String subject, String gradeLevel,
                String instructions, ExamStatus status, TimeConfig timeConfig,
                List<PreguntaExamen> questions, List<EvaluationCriteria> criteria) {
        this.id = id ;
        this.name = name;
        this.code = code;
        this.subject = subject;
        this.gradeLevel = gradeLevel;
        this.instructions = instructions;
        this.status = status != null ? status : ExamStatus.DRAFT;
        this.timeConfig = timeConfig;
        this.questions = questions != null ? new ArrayList<>(questions) : new ArrayList<>();
        this.criteria = criteria != null ? new ArrayList<>(criteria) : new ArrayList<>();
        this.createdAt = Instant.now();
        this.activo = false;
        calculateTotalPoints();
    }
    
    @Override
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public String getName() { return name; }
    public void setName(String name) { this.name = name; this.updatedAt = Instant.now(); }
    
    public String getCode() { return code; }
    public void setCode(String code) { this.code = code; this.updatedAt = Instant.now(); }
    
    public String getSubject() { return subject; }
    public void setSubject(String subject) { this.subject = subject; this.updatedAt = Instant.now(); }
    
    public String getGradeLevel() { return gradeLevel; }
    public void setGradeLevel(String gradeLevel) { this.gradeLevel = gradeLevel; this.updatedAt = Instant.now(); }
    
    public String getInstructions() { return instructions; }
    public void setInstructions(String instructions) { this.instructions = instructions; this.updatedAt = Instant.now(); }
    
    public ExamStatus getStatus() { return status; }
    public void setStatus(ExamStatus status) { this.status = status; this.updatedAt = Instant.now(); }
    
    public TimeConfig getTimeConfig() { return timeConfig; }
    public void setTimeConfig(TimeConfig timeConfig) { this.timeConfig = timeConfig; this.updatedAt = Instant.now(); }
    
    public List<PreguntaExamen> getQuestions() { return questions; }
    public void setQuestions(List<PreguntaExamen> questions) { 
        this.questions = questions; 
        calculateTotalPoints();
        this.updatedAt = Instant.now(); 
    }
    
    public List<EvaluationCriteria> getCriteria() { return criteria; }
    public void setCriteria(List<EvaluationCriteria> criteria) { this.criteria = criteria; this.updatedAt = Instant.now(); }
    
    public BigDecimal getTotalPoints() { return totalPoints; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public boolean isDeleted() { return activo; }
    public void setDeleted(boolean activo) { this.activo = activo; }
    
    public void addQuestion(PreguntaExamen question) {
        if (!status.canBeModified()) {
            throw new IllegalStateException("No se puede modificar el examen en estado: " + status);
        }
        this.questions.add(question);
        calculateTotalPoints();
        this.updatedAt = Instant.now();
    }
    
    public void removeQuestion(Long questionId) {
        if (!status.canBeModified()) {
            throw new IllegalStateException("No se puede modificar el examen en estado: " + status);
        }
        this.questions.removeIf(q -> q.getPreguntaId().equals(questionId));
        calculateTotalPoints();
        this.updatedAt = Instant.now();
    }
    
    public void schedule() {
        if (this.status != ExamStatus.DRAFT) {
            throw new IllegalStateException("Solo se pueden programar exámenes en estado BORRADOR");
        }
        if (this.questions.isEmpty()) {
            throw new IllegalStateException("No se puede programar un examen sin preguntas");
        }
        this.status = ExamStatus.SCHEDULED;
        this.updatedAt = Instant.now();
    }
    
    public void activate() {
        if (!this.status.canStart()) {
            throw new IllegalStateException("No se puede activar el examen en estado: " + status);
        }
        this.status = ExamStatus.ACTIVE;
        this.updatedAt = Instant.now();
    }
    
    public void complete() {
        if (this.status != ExamStatus.ACTIVE) {
            throw new IllegalStateException("Solo se pueden completar exámenes activos");
        }
        this.status = ExamStatus.COMPLETED;
        this.updatedAt = Instant.now();
    }
    
    public void archive() {
        if (this.status != ExamStatus.COMPLETED) {
            throw new IllegalStateException("Solo se pueden archivar exámenes completados");
        }
        this.status = ExamStatus.ARCHIVED;
        this.updatedAt = Instant.now();
    }
    
    public void markAsDeleted() {
        this.activo = true;
        this.updatedAt = Instant.now();
    }
    
    private void calculateTotalPoints() {
        this.totalPoints = questions.stream()
            .map(PreguntaExamen::getPuntos)
            .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    public void validate() {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("El nombre del examen es obligatorio");
        }
        if (timeConfig == null) {
            throw new IllegalArgumentException("La configuración de tiempo es obligatoria");
        }
    }
}

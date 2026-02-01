package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Entity
@Table(name = "questions")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "question_type", discriminatorType = DiscriminatorType.STRING)
public abstract class QuestionEntity {
    
    @Id
    private UUID id;
    
    @Column(name = "question_text", nullable = false, length = 2000)
    private String questionText;
    
    @Column(name = "question_type", insertable = false, updatable = false)
    private String questionType;
    
    @Column(name = "difficulty", nullable = false)
    private String difficulty;
    
    @Column(name = "max_score", nullable = false)
    private int maxScore;
    
    @Column(name = "theme_id")
    private UUID themeId;
    
    @Column(name = "hint", length = 1000)
    private String hint;
    
    @Column(name = "explanation", length = 2000)
    private String explanation;
    
    @ElementCollection
    @CollectionTable(name = "question_tags", joinColumns = @JoinColumn(name = "question_id"))
    @Column(name = "tag")
    private List<String> tags;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "metadata", columnDefinition = "json")
    private Map<String, Object> metadata;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "media", columnDefinition = "json")
    private List<MediaEmbeddable> media;
    
    @Column(name = "created_at", nullable = false, updatable = false)
    private Instant createdAt;
    
    @Column(name = "updated_at")
    private Instant updatedAt;
    
    @Column(name = "deleted", nullable = false)
    private boolean deleted = false;
    
    @Column(name = "version")
    private Integer version = 1;
    
    @PrePersist
    protected void onCreate() {
        if (id == null) id = UUID.randomUUID();
        if (createdAt == null) createdAt = Instant.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
    
    // Getters and Setters
    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    
    public String getQuestionText() { return questionText; }
    public void setQuestionText(String questionText) { this.questionText = questionText; }
    
    public String getQuestionType() { return questionType; }
    public void setQuestionType(String questionType) { this.questionType = questionType; }
    
    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }
    
    public int getMaxScore() { return maxScore; }
    public void setMaxScore(int maxScore) { this.maxScore = maxScore; }
    
    public UUID getThemeId() { return themeId; }
    public void setThemeId(UUID themeId) { this.themeId = themeId; }
    
    public String getHint() { return hint; }
    public void setHint(String hint) { this.hint = hint; }
    
    public String getExplanation() { return explanation; }
    public void setExplanation(String explanation) { this.explanation = explanation; }
    
    public List<String> getTags() { return tags; }
    public void setTags(List<String> tags) { this.tags = tags; }
    
    public Map<String, Object> getMetadata() { return metadata; }
    public void setMetadata(Map<String, Object> metadata) { this.metadata = metadata; }
    
    public List<MediaEmbeddable> getMedia() { return media; }
    public void setMedia(List<MediaEmbeddable> media) { this.media = media; }
    
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
    
    public boolean isDeleted() { return deleted; }
    public void setDeleted(boolean deleted) { this.deleted = deleted; }
    
    public Integer getVersion() { return version; }
    public void setVersion(Integer version) { this.version = version; }
}

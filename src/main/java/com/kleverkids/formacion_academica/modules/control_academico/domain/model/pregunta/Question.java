package com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Difficulty;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.Media;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.QuestionType;
import com.kleverkids.formacion_academica.shared.common.domain.AggregateRoot;

import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@JsonTypeInfo(
    use = JsonTypeInfo.Id.NAME,
    include = JsonTypeInfo.As.EXISTING_PROPERTY,
    property = "questionType",
    visible = true
)
@JsonSubTypes({
    @JsonSubTypes.Type(value = MultipleChoiceSingleQuestion.class, name = "multiple_choice_single"),
    @JsonSubTypes.Type(value = MultipleChoiceMultiQuestion.class, name = "multiple_choice_multi"),
    @JsonSubTypes.Type(value = TrueFalseQuestion.class, name = "true_false"),
    @JsonSubTypes.Type(value = OpenShortQuestion.class, name = "open_short"),
    @JsonSubTypes.Type(value = OpenLongQuestion.class, name = "open_long"),
    @JsonSubTypes.Type(value = NumericQuestion.class, name = "numeric"),
    @JsonSubTypes.Type(value = ScaleQuestion.class, name = "scale"),
    @JsonSubTypes.Type(value = OrderingQuestion.class, name = "ordering"),
    @JsonSubTypes.Type(value = MatchingQuestion.class, name = "matching")
})
public abstract class Question extends AggregateRoot<UUID> {
    
    protected UUID id;
    protected String questionText;
    protected QuestionType questionType;
    protected Difficulty difficulty;
    protected int maxScore;
    protected UUID themeId;
    protected List<Media> media;
    protected String hint;
    protected String explanation;
    protected List<String> tags;
    protected Map<String, Object> metadata;
    protected Instant createdAt;
    protected Instant updatedAt;
    protected boolean deleted;
    protected Integer version;
    
    protected Question() {}
    
    protected Question(UUID id, String questionText, QuestionType questionType, Difficulty difficulty,
                       int maxScore, UUID themeId, List<Media> media, String hint, String explanation,
                       List<String> tags, Map<String, Object> metadata) {
        if (questionText == null || questionText.isBlank()) {
            throw new IllegalArgumentException("Question text cannot be null or blank");
        }
        if (maxScore <= 0) {
            throw new IllegalArgumentException("Max score must be greater than 0");
        }
        
        this.id = id != null ? id : UUID.randomUUID();
        this.questionText = questionText;
        this.questionType = questionType;
        this.difficulty = difficulty != null ? difficulty : Difficulty.BASIC;
        this.maxScore = maxScore;
        this.themeId = themeId;
        this.media = media;
        this.hint = hint;
        this.explanation = explanation;
        this.tags = tags;
        this.metadata = metadata;
        this.createdAt = Instant.now();
        this.deleted = false;
        this.version = 1;
    }
    
    @Override
    public UUID getId() {
        return id;
    }
    
    public String getQuestionText() {
        return questionText;
    }
    
    public QuestionType getQuestionType() {
        return questionType;
    }
    
    public Difficulty getDifficulty() {
        return difficulty;
    }
    
    public int getMaxScore() {
        return maxScore;
    }
    
    public UUID getThemeId() {
        return themeId;
    }
    
    public List<Media> getMedia() {
        return media;
    }
    
    public String getHint() {
        return hint;
    }
    
    public String getExplanation() {
        return explanation;
    }
    
    public List<String> getTags() {
        return tags;
    }
    
    public Map<String, Object> getMetadata() {
        return metadata;
    }
    
    public Instant getCreatedAt() {
        return createdAt;
    }
    
    public Instant getUpdatedAt() {
        return updatedAt;
    }
    
    public boolean isDeleted() {
        return deleted;
    }
    
    public Integer getVersion() {
        return version;
    }
    
    public void setId(UUID id) {
        this.id = id;
    }
    
    public void setQuestionText(String questionText) {
        this.questionText = questionText;
        this.updatedAt = Instant.now();
    }
    
    public void setDifficulty(Difficulty difficulty) {
        this.difficulty = difficulty;
        this.updatedAt = Instant.now();
    }
    
    public void setMaxScore(int maxScore) {
        this.maxScore = maxScore;
        this.updatedAt = Instant.now();
    }
    
    public void setThemeId(UUID themeId) {
        this.themeId = themeId;
        this.updatedAt = Instant.now();
    }
    
    public void setMedia(List<Media> media) {
        this.media = media;
        this.updatedAt = Instant.now();
    }
    
    public void setHint(String hint) {
        this.hint = hint;
        this.updatedAt = Instant.now();
    }
    
    public void setExplanation(String explanation) {
        this.explanation = explanation;
        this.updatedAt = Instant.now();
    }
    
    public void setTags(List<String> tags) {
        this.tags = tags;
        this.updatedAt = Instant.now();
    }
    
    public void setMetadata(Map<String, Object> metadata) {
        this.metadata = metadata;
        this.updatedAt = Instant.now();
    }
    
    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
    
    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
    
    public void setVersion(Integer version) {
        this.version = version;
    }
    
    public void markAsDeleted() {
        this.deleted = true;
        this.updatedAt = Instant.now();
    }
    
    public void incrementVersion() {
        this.version = (this.version != null ? this.version : 0) + 1;
        this.updatedAt = Instant.now();
    }
    
    public abstract void validate();
}

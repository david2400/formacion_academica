package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@DiscriminatorValue("open_long")
public class OpenLongQuestionEntity extends QuestionEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rubric", columnDefinition = "json")
    private RubricEmbeddable rubric;
    
    @Column(name = "min_words")
    private Integer minWords;
    
    @Column(name = "max_words")
    private Integer maxWords;
    
    @Column(name = "allow_attachments")
    private boolean allowAttachments;
    
    public RubricEmbeddable getRubric() { return rubric; }
    public void setRubric(RubricEmbeddable rubric) { this.rubric = rubric; }
    
    public Integer getMinWords() { return minWords; }
    public void setMinWords(Integer minWords) { this.minWords = minWords; }
    
    public Integer getMaxWords() { return maxWords; }
    public void setMaxWords(Integer maxWords) { this.maxWords = maxWords; }
    
    public boolean isAllowAttachments() { return allowAttachments; }
    public void setAllowAttachments(boolean allowAttachments) { this.allowAttachments = allowAttachments; }
}

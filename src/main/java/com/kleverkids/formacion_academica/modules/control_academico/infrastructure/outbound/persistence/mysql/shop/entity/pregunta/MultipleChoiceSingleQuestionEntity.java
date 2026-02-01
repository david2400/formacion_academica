package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("multiple_choice_single")
public class MultipleChoiceSingleQuestionEntity extends QuestionEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "options", columnDefinition = "json")
    private List<OptionEmbeddable> options;
    
    @Column(name = "correct_option_id")
    private UUID correctOptionId;
    
    public List<OptionEmbeddable> getOptions() { return options; }
    public void setOptions(List<OptionEmbeddable> options) { this.options = options; }
    
    public UUID getCorrectOptionId() { return correctOptionId; }
    public void setCorrectOptionId(UUID correctOptionId) { this.correctOptionId = correctOptionId; }
}

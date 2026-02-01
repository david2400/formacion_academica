package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;
import java.util.UUID;

@Entity
@DiscriminatorValue("multiple_choice_multi")
public class MultipleChoiceMultiQuestionEntity extends QuestionEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "options", columnDefinition = "json")
    private List<OptionEmbeddable> options;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "correct_option_ids", columnDefinition = "json")
    private List<UUID> correctOptionIds;
    
    @Column(name = "min_selections")
    private Integer minSelections;
    
    @Column(name = "max_selections")
    private Integer maxSelections;
    
    public List<OptionEmbeddable> getOptions() { return options; }
    public void setOptions(List<OptionEmbeddable> options) { this.options = options; }
    
    public List<UUID> getCorrectOptionIds() { return correctOptionIds; }
    public void setCorrectOptionIds(List<UUID> correctOptionIds) { this.correctOptionIds = correctOptionIds; }
    
    public Integer getMinSelections() { return minSelections; }
    public void setMinSelections(Integer minSelections) { this.minSelections = minSelections; }
    
    public Integer getMaxSelections() { return maxSelections; }
    public void setMaxSelections(Integer maxSelections) { this.maxSelections = maxSelections; }
}

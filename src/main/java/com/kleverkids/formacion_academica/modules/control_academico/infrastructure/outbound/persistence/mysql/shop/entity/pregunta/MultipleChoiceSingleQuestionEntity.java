package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;


@Entity
@DiscriminatorValue("multiple_choice_single")
public class MultipleChoiceSingleQuestionEntity extends QuestionEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "options", columnDefinition = "json")
    private List<OptionEmbeddable> options;
    
    @Column(name = "correct_option_id")
    private Long correctOptionId;
    
    public List<OptionEmbeddable> getOptions() { return options; }
    public void setOptions(List<OptionEmbeddable> options) { this.options = options; }
    
    public Long getCorrectOptionId() { return correctOptionId; }
    public void setCorrectOptionId(Long correctOptionId) { this.correctOptionId = correctOptionId; }
}

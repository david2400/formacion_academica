package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;


@Entity
@DiscriminatorValue("multiple_choice_multi")
public class PreguntaOpcionMultipleEntity extends PreguntaEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "options", columnDefinition = "json")
    private List<OpcionEmbeddable> options;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "correct_option_ids", columnDefinition = "json")
    private List<Long> correctOptionIds;
    
    @Column(name = "min_selections")
    private Integer minSelections;
    
    @Column(name = "max_selections")
    private Integer maxSelections;
    
    public List<OpcionEmbeddable> getOptions() { return options; }
    public void setOptions(List<OpcionEmbeddable> options) { this.options = options; }
    
    public List<Long> getCorrectOptionIds() { return correctOptionIds; }
    public void setCorrectOptionIds(List<Long> correctOptionIds) { this.correctOptionIds = correctOptionIds; }
    
    public Integer getMinSelections() { return minSelections; }
    public void setMinSelections(Integer minSelections) { this.minSelections = minSelections; }
    
    public Integer getMaxSelections() { return maxSelections; }
    public void setMaxSelections(Integer maxSelections) { this.maxSelections = maxSelections; }
}

package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@DiscriminatorValue("open_short")
public class PreguntaAbiertaCortaEntity extends PreguntaEntity {
    
    @Column(name = "max_length")
    private Integer maxLength;
    
    @Column(name = "pattern")
    private String pattern;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "accepted_answers", columnDefinition = "json")
    private List<String> acceptedAnswers;
    
    @Column(name = "case_sensitive")
    private Boolean caseSensitive;
    
    public Integer getMaxLength() { return maxLength; }
    public void setMaxLength(Integer maxLength) { this.maxLength = maxLength; }
    
    public String getPattern() { return pattern; }
    public void setPattern(String pattern) { this.pattern = pattern; }
    
    public List<String> getAcceptedAnswers() { return acceptedAnswers; }
    public void setAcceptedAnswers(List<String> acceptedAnswers) { this.acceptedAnswers = acceptedAnswers; }
    
    public Boolean getCaseSensitive() { return caseSensitive; }
    public void setCaseSensitive(Boolean caseSensitive) { this.caseSensitive = caseSensitive; }
}

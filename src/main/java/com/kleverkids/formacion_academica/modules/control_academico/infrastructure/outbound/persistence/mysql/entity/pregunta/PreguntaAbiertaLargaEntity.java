package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@DiscriminatorValue("open_long")
public class PreguntaAbiertaLargaEntity extends PreguntaEntity {
    
    @Column(name = "min_length")
    private Integer minLength;
    
    @Column(name = "max_length")
    private Integer maxLength;
    
    @Column(name = "min_words")
    private Integer minWords;
    
    @Column(name = "max_words")
    private Integer maxWords;
    
    @Column(name = "requires_formatting")
    private Boolean requiresFormatting;
    
    @Column(name = "allow_attachments")
    private Boolean allowAttachments;
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "rubric", columnDefinition = "json")
    private RbricaEmbeddable rubric;
    
    public Integer getMinLength() { return minLength; }
    public void setMinLength(Integer minLength) { this.minLength = minLength; }
    
    public Integer getMaxLength() { return maxLength; }
    public void setMaxLength(Integer maxLength) { this.maxLength = maxLength; }
    
    public Integer getMinWords() { return minWords; }
    public void setMinWords(Integer minWords) { this.minWords = minWords; }
    
    public Integer getMaxWords() { return maxWords; }
    public void setMaxWords(Integer maxWords) { this.maxWords = maxWords; }
    
    public Boolean getRequiresFormatting() { return requiresFormatting; }
    public void setRequiresFormatting(Boolean requiresFormatting) { this.requiresFormatting = requiresFormatting; }
    
    public Boolean getAllowAttachments() { return allowAttachments; }
    public void setAllowAttachments(Boolean allowAttachments) { this.allowAttachments = allowAttachments; }
    
    public RbricaEmbeddable getRubric() { return rubric; }
    public void setRubric(RbricaEmbeddable rubric) { this.rubric = rubric; }
}

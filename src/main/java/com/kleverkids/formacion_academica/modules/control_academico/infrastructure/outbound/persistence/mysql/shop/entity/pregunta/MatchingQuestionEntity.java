package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@DiscriminatorValue("matching")
public class MatchingQuestionEntity extends QuestionEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "pairs", columnDefinition = "json")
    private List<MatchingPairEmbeddable> pairs;
    
    @Column(name = "partial_credit")
    private boolean partialCredit;
    
    public List<MatchingPairEmbeddable> getPairs() { return pairs; }
    public void setPairs(List<MatchingPairEmbeddable> pairs) { this.pairs = pairs; }
    
    public boolean isPartialCredit() { return partialCredit; }
    public void setPartialCredit(boolean partialCredit) { this.partialCredit = partialCredit; }
}

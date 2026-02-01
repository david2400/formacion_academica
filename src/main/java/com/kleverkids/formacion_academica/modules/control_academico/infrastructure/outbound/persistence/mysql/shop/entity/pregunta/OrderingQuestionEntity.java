package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.shop.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@DiscriminatorValue("ordering")
public class OrderingQuestionEntity extends QuestionEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "items", columnDefinition = "json")
    private List<OrderingItemEmbeddable> items;
    
    @Column(name = "partial_credit")
    private boolean partialCredit;
    
    public List<OrderingItemEmbeddable> getItems() { return items; }
    public void setItems(List<OrderingItemEmbeddable> items) { this.items = items; }
    
    public boolean isPartialCredit() { return partialCredit; }
    public void setPartialCredit(boolean partialCredit) { this.partialCredit = partialCredit; }
}

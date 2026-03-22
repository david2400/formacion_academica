package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@DiscriminatorValue("ordering")
public class PreguntaOrdenamientoEntity extends PreguntaEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "items", columnDefinition = "json")
    private List<OrderingItemEmbeddable> items;
    
    @Column(name = "partial_credit")
    private Boolean partialCredit;
    
    public List<OrderingItemEmbeddable> getItems() { return items; }
    public void setItems(List<OrderingItemEmbeddable> items) { this.items = items; }
    
    public Boolean getPartialCredit() { return partialCredit; }
    public void setPartialCredit(Boolean partialCredit) { this.partialCredit = partialCredit; }
}

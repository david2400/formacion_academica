package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.util.List;

@Entity
@DiscriminatorValue("matching")
public class PreguntaEmparejamientoEntity extends PreguntaEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "pairs", columnDefinition = "json")
    private List<ParEmparejamientoEmbeddable> pairs;
    
    @Column(name = "partial_credit")
    private Boolean partialCredit;
    
    public List<ParEmparejamientoEmbeddable> getPairs() { return pairs; }
    public void setPairs(List<ParEmparejamientoEmbeddable> pairs) { this.pairs = pairs; }
    
    public Boolean getPartialCredit() { return partialCredit; }
    public void setPartialCredit(Boolean partialCredit) { this.partialCredit = partialCredit; }
}

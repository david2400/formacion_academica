package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@DiscriminatorValue("scale")
public class PreguntaEscalaEntity extends PreguntaEntity {
    
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "scale_config", columnDefinition = "json")
    private ScaleConfigEmbeddable scaleConfig;
    
    @Column(name = "expected_value")
    private Integer expectedValue;
    
    public ScaleConfigEmbeddable getScaleConfig() { return scaleConfig; }
    public void setScaleConfig(ScaleConfigEmbeddable scaleConfig) { this.scaleConfig = scaleConfig; }
    
    public Integer getExpectedValue() { return expectedValue; }
    public void setExpectedValue(Integer expectedValue) { this.expectedValue = expectedValue; }
}

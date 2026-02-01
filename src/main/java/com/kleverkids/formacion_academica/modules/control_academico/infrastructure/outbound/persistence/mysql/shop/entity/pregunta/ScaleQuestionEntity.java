package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.persistence.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

@Entity
@DiscriminatorValue("scale")
public class ScaleQuestionEntity extends QuestionEntity {
    
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

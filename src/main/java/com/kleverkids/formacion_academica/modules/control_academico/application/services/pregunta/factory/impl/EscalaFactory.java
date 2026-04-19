package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ScaleConfigDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

@Component("scale")
public class EscalaFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.Scale) command;
        var entity = new PreguntaEscalaEntity();
        
        mapearCamposComunes(entity, command);
        entity.setScaleConfig(mapearConfigEscala(cmd.scaleConfig()));
        entity.setExpectedValue(cmd.expectedValue());
        
        return entity;
    }
    
    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.Scale) command;
        var pregunta = (PreguntaEscalaEntity) entity;
        
        mapearCamposComunes(pregunta, command);
        pregunta.setScaleConfig(mapearConfigEscala(cmd.scaleConfig()));
        pregunta.setExpectedValue(cmd.expectedValue());
        
        return pregunta;
    }
    
    @Override
    public String getTipoPregunta() {
        return "scale";
    }
    
    private ScaleConfigEmbeddable mapearConfigEscala(ScaleConfigDto config) {
        if (config == null) return null;
        
        ScaleConfigEmbeddable configEmb = new ScaleConfigEmbeddable();
        configEmb.setMinValue(config.minValue());
        configEmb.setMaxValue(config.maxValue());
        configEmb.setMinLabel(config.minLabel());
        configEmb.setMaxLabel(config.maxLabel());
        configEmb.setLabels(config.labels());
        
        return configEmb;
    }
}

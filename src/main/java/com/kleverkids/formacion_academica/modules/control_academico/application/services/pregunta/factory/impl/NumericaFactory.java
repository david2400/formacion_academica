package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

@Component("numeric")
public class NumericaFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.Numeric) command;
        var entity = new PreguntaNumericaEntity();
        
        mapearCamposComunes(entity, command);
        entity.setCorrectValue(cmd.correctValue());
        entity.setTolerance(cmd.tolerance());
        entity.setUnit(cmd.unit());
        entity.setDecimalPlaces(cmd.decimalPlaces());
        
        return entity;
    }
    
    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.Numeric) command;
        var pregunta = (PreguntaNumericaEntity) entity;
        
        mapearCamposComunes(pregunta, command);
        pregunta.setCorrectValue(cmd.correctValue());
        pregunta.setTolerance(cmd.tolerance());
        pregunta.setUnit(cmd.unit());
        pregunta.setDecimalPlaces(cmd.decimalPlaces());
        
        return pregunta;
    }
    
    @Override
    public String getTipoPregunta() {
        return "numeric";
    }
}

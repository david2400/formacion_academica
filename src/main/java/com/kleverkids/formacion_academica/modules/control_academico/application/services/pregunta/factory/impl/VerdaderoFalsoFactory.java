package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

@Component("true_false")
public class VerdaderoFalsoFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.TrueFalse) command;
        var entity = new PreguntaVerdaderoFalsoEntity();
        
        mapearCamposComunes(entity, command);
        entity.setCorrectAnswer(cmd.correctAnswer());
        
        return entity;
    }
    
    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.TrueFalse) command;
        var pregunta = (PreguntaVerdaderoFalsoEntity) entity;
        
        mapearCamposComunes(pregunta, command);
        pregunta.setCorrectAnswer(cmd.correctAnswer());
        
        return pregunta;
    }
    
    @Override
    public String getTipoPregunta() {
        return "true_false";
    }
}

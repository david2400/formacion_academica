package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

@Component("open_short")
public class AbiertaCortaFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.OpenShort) command;
        var entity = new PreguntaAbiertaCortaEntity();
        
        mapearCamposComunes(entity, command);
        entity.setAcceptedAnswers(cmd.acceptedAnswers());
        entity.setCaseSensitive(cmd.caseSensitive());
        entity.setMaxLength(cmd.maxLength());
        
        return entity;
    }
    
    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.OpenShort) command;
        var pregunta = (PreguntaAbiertaCortaEntity) entity;
        
        mapearCamposComunes(pregunta, command);
        pregunta.setAcceptedAnswers(cmd.acceptedAnswers());
        pregunta.setCaseSensitive(cmd.caseSensitive());
        pregunta.setMaxLength(cmd.maxLength());
        
        return pregunta;
    }
    
    @Override
    public String getTipoPregunta() {
        return "open_short";
    }
}

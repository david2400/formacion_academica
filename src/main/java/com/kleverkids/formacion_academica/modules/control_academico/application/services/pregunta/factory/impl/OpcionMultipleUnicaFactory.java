package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.OptionDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("multiple_choice_single")
public class OpcionMultipleUnicaFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.MultipleChoiceSingle) command;
        var entity = new PreguntaOpcionMultipleUnicaEntity();
        
        mapearCamposComunes(entity, command);
        entity.setOptions(mapearOpciones(cmd.options()));
        entity.setCorrectOptionId(cmd.correctOptionId());
        
        return entity;
    }
    
    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.MultipleChoiceSingle) command;
        var pregunta = (PreguntaOpcionMultipleUnicaEntity) entity;
        
        mapearCamposComunes(pregunta, command);
        pregunta.setOptions(mapearOpciones(cmd.options()));
        pregunta.setCorrectOptionId(cmd.correctOptionId());
        
        return pregunta;
    }
    
    @Override
    public String getTipoPregunta() {
        return "multiple_choice_single";
    }
    
    private List<OpcionEmbeddable> mapearOpciones(List<OptionDto> options) {
        if (options == null) return null;
        
        return options.stream()
            .map(opt -> {
                OpcionEmbeddable opcion = new OpcionEmbeddable();
                opcion.setId(opt.getId());
                opcion.setTexto(opt.getText());
                if (opt.getMedia() != null) {
                    MediaEmbeddable media = new MediaEmbeddable();
                    media.setType(opt.getMedia().getType());
                    media.setUrl(opt.getMedia().getUrl());
                    opcion.setMedia(media);
                }
                opcion.setCorrecta(false);
                return opcion;
            })
            .collect(Collectors.toList());
    }
}

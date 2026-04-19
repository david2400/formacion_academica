package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.OptionDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("multiple_choice_multi")
public class OpcionMultipleMultipleFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.MultipleChoiceMulti) command;
        var entity = new PreguntaOpcionMultipleEntity();
        
        mapearCamposComunes(entity, command);
        entity.setOptions(mapearOpciones(cmd.options()));
        entity.setCorrectOptionIds(cmd.correctOptionIds());
        entity.setMinSelections(cmd.minSelections());
        entity.setMaxSelections(cmd.maxSelections());
        
        return entity;
    }
    
    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.MultipleChoiceMulti) command;
        var pregunta = (PreguntaOpcionMultipleEntity) entity;
        
        mapearCamposComunes(pregunta, command);
        pregunta.setOptions(mapearOpciones(cmd.options()));
        pregunta.setCorrectOptionIds(cmd.correctOptionIds());
        pregunta.setMinSelections(cmd.minSelections());
        pregunta.setMaxSelections(cmd.maxSelections());
        
        return pregunta;
    }
    
    @Override
    public String getTipoPregunta() {
        return "multiple_choice_multi";
    }
    
    private List<OpcionEmbeddable> mapearOpciones(List<OptionDto> options) {
        if (options == null) return null;
        
        return options.stream()
            .map(opt -> {
                OpcionEmbeddable opcion = new OpcionEmbeddable();
                opcion.setId(opt.id());
                opcion.setTexto(opt.text());
                if (opt.media() != null) {
                    MediaEmbeddable media = new MediaEmbeddable();
                    media.setType(opt.media().type());
                    media.setUrl(opt.media().url());
                    opcion.setMedia(media);
                }
                opcion.setCorrecta(false);
                return opcion;
            })
            .collect(Collectors.toList());
    }
}

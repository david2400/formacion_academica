package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.OptionDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Component("multiple_choice_multi")
public class OpcionMultipleMultipleFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.MultipleChoiceMulti) command;
        var entity = new PreguntaOpcionMultipleEntity();

        mapearCamposComunes(entity, command);
        aplicarOpciones(entity, cmd);
        entity.setMinSelections(cmd.minSelections());
        entity.setMaxSelections(cmd.maxSelections());

        return entity;
    }

    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.MultipleChoiceMulti) command;
        var pregunta = (PreguntaOpcionMultipleEntity) entity;

        mapearCamposComunes(pregunta, command);
        aplicarOpciones(pregunta, cmd);
        pregunta.setMinSelections(cmd.minSelections());
        pregunta.setMaxSelections(cmd.maxSelections());

        return pregunta;
    }

    /**
     * Asigna opciones y respuestas correctas reconciliando las dos formas de marcarlas:
     * el flag is_correct de cada opción y la lista correct_option_ids. Si sólo llega
     * una de las dos, se deriva la otra.
     */
    private void aplicarOpciones(PreguntaOpcionMultipleEntity entity,
                                 CreateQuestionCommand.MultipleChoiceMulti cmd) {
        List<OpcionEmbeddable> opciones = mapearOpciones(cmd.options());
        List<Long> correctOptionIds = cmd.correctOptionIds();

        if (opciones != null) {
            if (correctOptionIds == null || correctOptionIds.isEmpty()) {
                correctOptionIds = opciones.stream()
                    .filter(OpcionEmbeddable::esCorrecta)
                    .map(OpcionEmbeddable::getId)
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
            } else {
                Set<Long> idsCorrectas = new HashSet<>(correctOptionIds);
                opciones.forEach(opcion -> opcion.setCorrecta(idsCorrectas.contains(opcion.getId())));
            }
        }

        entity.setOptions(opciones);
        entity.setCorrectOptionIds(correctOptionIds);
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
                opcion.setId(opt.getId());
                opcion.setTexto(opt.getText());
                if (opt.getMedia() != null) {
                    MediaEmbeddable media = new MediaEmbeddable();
                    media.setType(opt.getMedia().getType());
                    media.setUrl(opt.getMedia().getUrl());
                    opcion.setMedia(media);
                }
                opcion.setCorrecta(Boolean.TRUE.equals(opt.getIsCorrect()));
                return opcion;
            })
            .collect(Collectors.toList());
    }
}

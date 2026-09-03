package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.OptionDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component("multiple_choice_single")
public class OpcionMultipleUnicaFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.MultipleChoiceSingle) command;
        var entity = new PreguntaOpcionMultipleUnicaEntity();

        mapearCamposComunes(entity, command);
        aplicarOpciones(entity, cmd);

        return entity;
    }

    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.MultipleChoiceSingle) command;
        var pregunta = (PreguntaOpcionMultipleUnicaEntity) entity;

        mapearCamposComunes(pregunta, command);
        aplicarOpciones(pregunta, cmd);

        return pregunta;
    }

    /**
     * Asigna opciones y respuesta correcta reconciliando las dos formas de marcarla:
     * el flag is_correct de cada opción y el campo correct_option_id. Si sólo llega
     * una de las dos, se deriva la otra.
     */
    private void aplicarOpciones(PreguntaOpcionMultipleUnicaEntity entity,
                                 CreateQuestionCommand.MultipleChoiceSingle cmd) {
        List<OpcionEmbeddable> opciones = mapearOpciones(cmd.options());
        Long correctOptionId = cmd.correctOptionId();

        if (opciones != null) {
            if (correctOptionId == null) {
                correctOptionId = opciones.stream()
                    .filter(OpcionEmbeddable::esCorrecta)
                    .map(OpcionEmbeddable::getId)
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
            } else {
                final Long idCorrecta = correctOptionId;
                opciones.forEach(opcion -> opcion.setCorrecta(idCorrecta.equals(opcion.getId())));
            }
        }

        entity.setOptions(opciones);
        entity.setCorrectOptionId(correctOptionId);
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
                opcion.setCorrecta(Boolean.TRUE.equals(opt.getIsCorrect()));
                return opcion;
            })
            .collect(Collectors.toList());
    }
}

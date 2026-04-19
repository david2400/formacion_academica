package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.MediaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.MediaEmbeddable;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaEntity;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseFactory {
    
    protected void mapearCamposComunes(PreguntaEntity entity, CreateQuestionCommand command) {
        entity.setTextoPregunta(command.questionText());
        entity.setDificultad(command.difficulty());
        entity.setPuntajeMaximo(command.maxScore());
        entity.setTemaId(command.themeId());
        entity.setHint(command.hint());
        entity.setExplanation(command.explanation());
        entity.setTags(command.tags());
        entity.setMetadata(command.metadata());
        entity.setMedia(mapearMedia(command.media()));
    }
    
    protected List<MediaEmbeddable> mapearMedia(List<MediaDto> media) {
        if (media == null) return null;
        
        return media.stream()
            .map(m -> {
                MediaEmbeddable mediaEmb = new MediaEmbeddable();
                mediaEmb.setType(m.type());
                mediaEmb.setUrl(m.url());
                return mediaEmb;
            })
            .collect(Collectors.toList());
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.MatchingPairDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("matching")
public class EmparejamientoFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.Matching) command;
        var entity = new PreguntaEmparejamientoEntity();
        
        mapearCamposComunes(entity, command);
        entity.setPairs(mapearPares(cmd.pairs()));
        entity.setPartialCredit(cmd.partialCredit());
        
        return entity;
    }
    
    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.Matching) command;
        var pregunta = (PreguntaEmparejamientoEntity) entity;
        
        mapearCamposComunes(pregunta, command);
        pregunta.setPairs(mapearPares(cmd.pairs()));
        pregunta.setPartialCredit(cmd.partialCredit());
        
        return pregunta;
    }
    
    @Override
    public String getTipoPregunta() {
        return "matching";
    }
    
    private List<ParEmparejamientoEmbeddable> mapearPares(List<MatchingPairDto> pairs) {
        if (pairs == null) return null;
        
        return pairs.stream()
            .map(pair -> {
                ParEmparejamientoEmbeddable parEmb = new ParEmparejamientoEmbeddable();
                parEmb.setId(pair.getId());
                parEmb.setItemIzquierdo(pair.getLeftItem());
                parEmb.setItemDerecho(pair.getRightItem());
                if (pair.getLeftMedia() != null) {
                    MediaEmbeddable leftMedia = new MediaEmbeddable();
                    leftMedia.setType(pair.getLeftMedia().getType());
                    leftMedia.setUrl(pair.getLeftMedia().getUrl());
                    parEmb.setMediaIzquierda(leftMedia);
                }
                if (pair.getRightMedia() != null) {
                    MediaEmbeddable rightMedia = new MediaEmbeddable();
                    rightMedia.setType(pair.getRightMedia().getType());
                    rightMedia.setUrl(pair.getRightMedia().getUrl());
                    parEmb.setMediaDerecha(rightMedia);
                }
                return parEmb;
            })
            .collect(Collectors.toList());
    }
}

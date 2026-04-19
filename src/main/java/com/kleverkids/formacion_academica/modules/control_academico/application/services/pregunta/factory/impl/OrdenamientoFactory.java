package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.OrderingItemDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component("ordering")
public class OrdenamientoFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.Ordering) command;
        var entity = new PreguntaOrdenamientoEntity();
        
        mapearCamposComunes(entity, command);
        entity.setItems(mapearItems(cmd.items()));
        entity.setPartialCredit(cmd.partialCredit());
        
        return entity;
    }
    
    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.Ordering) command;
        var pregunta = (PreguntaOrdenamientoEntity) entity;
        
        mapearCamposComunes(pregunta, command);
        pregunta.setItems(mapearItems(cmd.items()));
        pregunta.setPartialCredit(cmd.partialCredit());
        
        return pregunta;
    }
    
    @Override
    public String getTipoPregunta() {
        return "ordering";
    }
    
    private List<OrderingItemEmbeddable> mapearItems(List<OrderingItemDto> items) {
        if (items == null) return null;
        
        return items.stream()
            .map(item -> {
                OrderingItemEmbeddable itemEmb = new OrderingItemEmbeddable();
                itemEmb.setId(item.id());
                itemEmb.setText(item.text());
                itemEmb.setCorrectPosition(item.correctPosition());
                return itemEmb;
            })
            .collect(Collectors.toList());
    }
}

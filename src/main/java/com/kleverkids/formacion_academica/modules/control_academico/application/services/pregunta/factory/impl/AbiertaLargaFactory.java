package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.impl;

import com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory.PreguntaEntityFactory;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.RubricaDto;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.*;
import org.springframework.stereotype.Component;

@Component("open_long")
public class AbiertaLargaFactory extends BaseFactory implements PreguntaEntityFactory {
    
    @Override
    public PreguntaEntity crear(CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.OpenLong) command;
        var entity = new PreguntaAbiertaLargaEntity();
        
        mapearCamposComunes(entity, command);
        entity.setRubric(mapearRubrica(cmd.rubric()));
        entity.setMinWords(cmd.minWords());
        entity.setMaxWords(cmd.maxWords());
        entity.setAllowAttachments(cmd.allowAttachments());
        
        return entity;
    }
    
    @Override
    public PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command) {
        var cmd = (CreateQuestionCommand.OpenLong) command;
        var pregunta = (PreguntaAbiertaLargaEntity) entity;
        
        mapearCamposComunes(pregunta, command);
        pregunta.setRubric(mapearRubrica(cmd.rubric()));
        pregunta.setMinWords(cmd.minWords());
        pregunta.setMaxWords(cmd.maxWords());
        pregunta.setAllowAttachments(cmd.allowAttachments());
        
        return pregunta;
    }
    
    @Override
    public String getTipoPregunta() {
        return "open_long";
    }
    
    private RbricaEmbeddable mapearRubrica(RubricaDto rubrica) {
        if (rubrica == null || rubrica.criterios() == null) return null;
        
        var criteriosEmb = rubrica.criterios().stream()
            .map(crit -> {
                var nivelesEmb = crit.niveles().stream()
                    .map(niv -> new RbricaEmbeddable.NivelEmbeddable(
                        niv.nombre(),
                        niv.descripcion(),
                        niv.puntaje()
                    ))
                    .toList();
                
                return new RbricaEmbeddable.CriterioEmbeddable(
                    crit.nombre(),
                    crit.descripcion(),
                    crit.puntajeMaximo(),
                    nivelesEmb
                );
            })
            .toList();
        
        return new RbricaEmbeddable(criteriosEmb);
    }
}

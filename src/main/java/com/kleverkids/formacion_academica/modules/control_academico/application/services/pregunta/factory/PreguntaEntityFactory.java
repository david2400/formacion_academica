package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta.factory;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.CreateQuestionCommand;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.pregunta.PreguntaEntity;

public interface PreguntaEntityFactory {
    PreguntaEntity crear(CreateQuestionCommand command);
    PreguntaEntity actualizar(PreguntaEntity entity, CreateQuestionCommand command);
    String getTipoPregunta();
}

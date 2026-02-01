package com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

public interface QuestionEventPublisher {
    
    void publish(DomainEvent event);
}

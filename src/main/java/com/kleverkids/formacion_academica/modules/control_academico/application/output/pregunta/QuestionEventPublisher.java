package com.kleverkids.formacion_academica.modules.questions.application.port.out;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

public interface QuestionEventPublisher {
    
    void publish(DomainEvent event);
}

package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.pregunta.QuestionEventPublisher;
import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class QuestionEventPublisherAdapter implements QuestionEventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(QuestionEventPublisherAdapter.class);
    
    private final ApplicationEventPublisher applicationEventPublisher;

    
    @Override
    public void publish(DomainEvent event) {
        log.info("Publishing domain event: {} with id: {}", event.getEventType(), event.getEventId());
        applicationEventPublisher.publishEvent(event);
    }
}

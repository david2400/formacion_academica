package com.kleverkids.formacion_academica.modules.questions.infrastructure.adapter.out.messaging;

import com.kleverkids.formacion_academica.modules.questions.application.port.out.QuestionEventPublisher;
import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class QuestionEventPublisherAdapter implements QuestionEventPublisher {
    
    private static final Logger log = LoggerFactory.getLogger(QuestionEventPublisherAdapter.class);
    
    private final ApplicationEventPublisher applicationEventPublisher;
    
    public QuestionEventPublisherAdapter(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
    
    @Override
    public void publish(DomainEvent event) {
        log.info("Publishing domain event: {} with id: {}", event.getEventType(), event.getEventId());
        applicationEventPublisher.publishEvent(event);
    }
}

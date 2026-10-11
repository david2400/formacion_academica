package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackEventPublisher;
import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/** Mismo publicador síncrono in-process que el resto del módulo (sin broker). */
@Slf4j
@Component
@RequiredArgsConstructor
public class FeedbackEventPublisherAdapter implements FeedbackEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Override
    public void publish(DomainEvent event) {
        log.debug("Publicando evento {} ({})", event.getEventType(), event.getEventId());
        applicationEventPublisher.publishEvent(event);
    }
}

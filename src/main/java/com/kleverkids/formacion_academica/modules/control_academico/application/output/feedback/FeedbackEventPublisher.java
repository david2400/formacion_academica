package com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

public interface FeedbackEventPublisher {
    void publish(DomainEvent event);
}

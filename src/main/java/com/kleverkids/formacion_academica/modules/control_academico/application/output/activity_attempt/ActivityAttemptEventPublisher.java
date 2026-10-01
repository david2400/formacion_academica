package com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_attempt;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

public interface ActivityAttemptEventPublisher {

    void publish(DomainEvent event);
}

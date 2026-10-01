package com.kleverkids.formacion_academica.modules.control_academico.application.output.activity;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

public interface ActivityEventPublisher {

    void publish(DomainEvent event);
}

package com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_assignment;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

public interface LearningAssignmentEventPublisher {

    void publish(DomainEvent event);
}

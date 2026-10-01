package com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence;

import com.kleverkids.formacion_academica.shared.common.domain.DomainEvent;

public interface LearningSequenceEventPublisher {

    void publish(DomainEvent event);
}

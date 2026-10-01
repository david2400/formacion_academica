package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;

import java.util.Optional;

public interface ConsultarLearningSequenceUseCase {

    Optional<LearningSequence> consultarPorId(Long id);
}

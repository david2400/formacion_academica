package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;

public interface ArchivarLearningSequenceUseCase {

    LearningSequence archivar(Long id);
}

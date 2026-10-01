package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;

/** Congela una LearningSequenceVersion nueva y marca la secuencia como PUBLISHED. */
public interface PublicarLearningSequenceUseCase {

    LearningSequence publicar(Long id);
}

package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;

/** Crea una copia DRAFT de la secuencia, sus items y sus dependencias; no copia versiones. */
public interface DuplicarLearningSequenceUseCase {

    LearningSequence duplicar(Long id);
}

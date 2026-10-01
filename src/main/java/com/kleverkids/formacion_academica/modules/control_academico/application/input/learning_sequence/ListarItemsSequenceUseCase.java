package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;

import java.util.List;

public interface ListarItemsSequenceUseCase {

    /** Devuelve todos los items de la secuencia (raíz + anidados), ordenados. */
    List<LearningSequenceItem> listarItems(Long sequenceId);
}

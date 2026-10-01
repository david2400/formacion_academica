package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.AgregarItemSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;

public interface AgregarItemSequenceUseCase {

    LearningSequenceItem agregarItem(Long sequenceId, AgregarItemSequenceDto request);
}

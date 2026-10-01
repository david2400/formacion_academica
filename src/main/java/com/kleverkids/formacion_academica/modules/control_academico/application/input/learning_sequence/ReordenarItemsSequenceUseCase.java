package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ReordenarItemsSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;

import java.util.List;

public interface ReordenarItemsSequenceUseCase {

    List<LearningSequenceItem> reordenar(Long sequenceId, ReordenarItemsSequenceDto request);
}

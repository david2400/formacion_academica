package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ReordenarItemsSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ReordenarItemsSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReordenarItemsSequenceService implements ReordenarItemsSequenceUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;

    @Override
    public List<LearningSequenceItem> reordenar(Long sequenceId, ReordenarItemsSequenceDto request) {
        return learningSequenceRepositoryPort.reordenar(sequenceId, request);
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ActualizarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.ActualizarLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarLearningSequenceService implements ActualizarLearningSequenceUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;

    @Override
    public LearningSequence actualizar(ActualizarLearningSequenceDto request) {
        return learningSequenceRepositoryPort.actualizar(request);
    }
}

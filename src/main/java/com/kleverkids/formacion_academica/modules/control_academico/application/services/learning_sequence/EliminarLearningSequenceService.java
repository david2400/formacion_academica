package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.EliminarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarLearningSequenceService implements EliminarLearningSequenceUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;

    @Override
    public void eliminar(Long id) {
        learningSequenceRepositoryPort.eliminar(id);
    }
}

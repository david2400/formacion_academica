package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.DuplicarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DuplicarLearningSequenceService implements DuplicarLearningSequenceUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;

    @Override
    public LearningSequence duplicar(Long id) {
        learningSequenceRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Secuencia no encontrada"));
        return learningSequenceRepositoryPort.duplicar(id);
    }
}

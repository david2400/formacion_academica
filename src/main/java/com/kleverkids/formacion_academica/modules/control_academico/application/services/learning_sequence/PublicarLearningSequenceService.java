package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.PublicarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceEventPublisher;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequenceItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PublicarLearningSequenceService implements PublicarLearningSequenceUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;
    private final LearningSequenceEventPublisher learningSequenceEventPublisher;

    @Override
    public LearningSequence publicar(Long id) {
        learningSequenceRepositoryPort.obtenerPorId(id)
                .orElseThrow(() -> new IllegalArgumentException("Secuencia no encontrada"));

        List<LearningSequenceItem> items = learningSequenceRepositoryPort.listarItems(id);
        if (items.isEmpty()) {
            throw new IllegalStateException(
                    "La secuencia debe tener al menos una actividad o grupo para poder publicarse");
        }

        LearningSequence publicada = learningSequenceRepositoryPort.publicar(id);
        publicada.registrarEventoPublicacion();
        publicada.getDomainEvents().forEach(learningSequenceEventPublisher::publish);
        publicada.clearDomainEvents();
        return publicada;
    }
}

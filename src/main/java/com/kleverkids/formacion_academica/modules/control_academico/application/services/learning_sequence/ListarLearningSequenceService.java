package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.ListarLearningSequenceUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.FiltroLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarLearningSequenceService implements ListarLearningSequenceUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;

    @Override
    public List<LearningSequence> listarTodas() {
        return learningSequenceRepositoryPort.listarTodas();
    }

    @Override
    public List<LearningSequence> buscar(FiltroLearningSequenceDto filtro) {
        if (filtro == null || filtro.estaVacio()) {
            return learningSequenceRepositoryPort.listarTodas();
        }
        return learningSequenceRepositoryPort.buscar(filtro);
    }
}

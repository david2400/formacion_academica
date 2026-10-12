package com.kleverkids.formacion_academica.modules.control_academico.application.services.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence.EliminarDependenciaUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.learning_sequence.LearningSequenceRepositoryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EliminarDependenciaService implements EliminarDependenciaUseCase {

    private final LearningSequenceRepositoryPort learningSequenceRepositoryPort;

    @Override
    public void eliminarDependencia(Long itemId, Long dependencyId) {
        learningSequenceRepositoryPort.eliminarDependencia(itemId, dependencyId);
    }
}

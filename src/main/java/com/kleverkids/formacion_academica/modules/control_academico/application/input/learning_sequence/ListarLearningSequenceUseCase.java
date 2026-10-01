package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.FiltroLearningSequenceDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.LearningSequence;

import java.util.List;

public interface ListarLearningSequenceUseCase {

    List<LearningSequence> listarTodas();

    List<LearningSequence> buscar(FiltroLearningSequenceDto filtro);
}

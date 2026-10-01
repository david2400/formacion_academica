package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.learning_sequence.DefinirDependenciaDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;

public interface DefinirDependenciaUseCase {

    ActivityDependencyRule definir(Long sequenceId, Long itemId, DefinirDependenciaDto request);
}

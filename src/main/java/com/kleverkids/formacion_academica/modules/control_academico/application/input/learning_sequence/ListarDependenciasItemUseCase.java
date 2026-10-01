package com.kleverkids.formacion_academica.modules.control_academico.application.input.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.ActivityDependencyRule;

import java.util.List;

public interface ListarDependenciasItemUseCase {

    List<ActivityDependencyRule> listarDependencias(Long itemId);
}

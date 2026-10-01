package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;

import java.util.Optional;

public interface ConsultarActivityTypeDefinitionUseCase {

    Optional<ActivityTypeDefinition> consultarPorId(Long id);

    Optional<ActivityTypeDefinition> consultarPorTipo(String type);
}

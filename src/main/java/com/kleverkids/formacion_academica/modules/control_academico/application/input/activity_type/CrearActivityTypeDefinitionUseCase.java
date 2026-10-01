package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.CrearActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;

public interface CrearActivityTypeDefinitionUseCase {

    ActivityTypeDefinition crear(CrearActivityTypeDefinitionDto request);
}

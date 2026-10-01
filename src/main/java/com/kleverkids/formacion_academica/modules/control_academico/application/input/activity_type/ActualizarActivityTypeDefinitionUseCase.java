package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.ActualizarActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;

public interface ActualizarActivityTypeDefinitionUseCase {

    ActivityTypeDefinition actualizar(ActualizarActivityTypeDefinitionDto request);
}

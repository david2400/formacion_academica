package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.ActualizarActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.ActualizarActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActualizarActivityTypeDefinitionService implements ActualizarActivityTypeDefinitionUseCase {

    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;

    @Override
    public ActivityTypeDefinition actualizar(ActualizarActivityTypeDefinitionDto request) {
        return activityTypeDefinitionRepositoryPort.actualizar(request);
    }
}

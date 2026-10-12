package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.CrearActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.CrearActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CrearActivityTypeDefinitionService implements CrearActivityTypeDefinitionUseCase {

    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;

    @Override
    public ActivityTypeDefinition crear(CrearActivityTypeDefinitionDto request) {
        if (activityTypeDefinitionRepositoryPort.existePorTipo(request.getType())) {
            throw new IllegalArgumentException(
                    "Ya existe un tipo de actividad registrado con el código '" + request.getType() + "'");
        }
        return activityTypeDefinitionRepositoryPort.guardar(request);
    }
}

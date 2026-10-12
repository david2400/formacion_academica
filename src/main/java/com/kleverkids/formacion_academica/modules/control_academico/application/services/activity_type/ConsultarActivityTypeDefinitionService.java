package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.ConsultarActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ConsultarActivityTypeDefinitionService implements ConsultarActivityTypeDefinitionUseCase {

    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;

    @Override
    public Optional<ActivityTypeDefinition> consultarPorId(Long id) {
        return activityTypeDefinitionRepositoryPort.obtenerPorId(id);
    }

    @Override
    public Optional<ActivityTypeDefinition> consultarPorTipo(String type) {
        return activityTypeDefinitionRepositoryPort.obtenerPorTipo(type);
    }
}

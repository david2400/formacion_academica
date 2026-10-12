package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.ListarActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ListarActivityTypeDefinitionService implements ListarActivityTypeDefinitionUseCase {

    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;

    @Override
    public List<ActivityTypeDefinition> listarActivos() {
        return activityTypeDefinitionRepositoryPort.listarActivos();
    }

    @Override
    public List<ActivityTypeDefinition> listarTodos() {
        return activityTypeDefinitionRepositoryPort.listarTodos();
    }
}

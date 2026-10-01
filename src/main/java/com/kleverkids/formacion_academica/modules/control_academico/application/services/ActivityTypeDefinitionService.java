package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.ActualizarActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.ConsultarActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.CrearActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type.ListarActivityTypeDefinitionUseCase;
import com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type.ActivityTypeDefinitionRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.ActualizarActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.CrearActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class ActivityTypeDefinitionService implements CrearActivityTypeDefinitionUseCase,
        ActualizarActivityTypeDefinitionUseCase,
        ConsultarActivityTypeDefinitionUseCase,
        ListarActivityTypeDefinitionUseCase {

    private final ActivityTypeDefinitionRepositoryPort activityTypeDefinitionRepositoryPort;

    @Override
    public ActivityTypeDefinition crear(CrearActivityTypeDefinitionDto request) {
        if (activityTypeDefinitionRepositoryPort.existePorTipo(request.getType())) {
            throw new IllegalArgumentException(
                    "Ya existe un tipo de actividad registrado con el código '" + request.getType() + "'");
        }
        return activityTypeDefinitionRepositoryPort.guardar(request);
    }

    @Override
    public ActivityTypeDefinition actualizar(ActualizarActivityTypeDefinitionDto request) {
        return activityTypeDefinitionRepositoryPort.actualizar(request);
    }

    @Override
    public Optional<ActivityTypeDefinition> consultarPorId(Long id) {
        return activityTypeDefinitionRepositoryPort.obtenerPorId(id);
    }

    @Override
    public Optional<ActivityTypeDefinition> consultarPorTipo(String type) {
        return activityTypeDefinitionRepositoryPort.obtenerPorTipo(type);
    }

    @Override
    public List<ActivityTypeDefinition> listarActivos() {
        return activityTypeDefinitionRepositoryPort.listarActivos();
    }

    @Override
    public List<ActivityTypeDefinition> listarTodos() {
        return activityTypeDefinitionRepositoryPort.listarTodos();
    }
}

package com.kleverkids.formacion_academica.modules.control_academico.application.output.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.ActualizarActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_type.CrearActivityTypeDefinitionDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;

import java.util.List;
import java.util.Optional;

public interface ActivityTypeDefinitionRepositoryPort {

    ActivityTypeDefinition guardar(CrearActivityTypeDefinitionDto dto);

    ActivityTypeDefinition actualizar(ActualizarActivityTypeDefinitionDto dto);

    Optional<ActivityTypeDefinition> obtenerPorId(Long id);

    Optional<ActivityTypeDefinition> obtenerPorTipo(String type);

    List<ActivityTypeDefinition> listarActivos();

    List<ActivityTypeDefinition> listarTodos();

    boolean existePorTipo(String type);
}

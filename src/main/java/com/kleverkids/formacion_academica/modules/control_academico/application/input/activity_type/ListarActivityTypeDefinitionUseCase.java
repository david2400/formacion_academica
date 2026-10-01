package com.kleverkids.formacion_academica.modules.control_academico.application.input.activity_type;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.ActivityTypeDefinition;

import java.util.List;

public interface ListarActivityTypeDefinitionUseCase {

    /** Para el panel "+ Agregar actividad": solo los tipos activos. */
    List<ActivityTypeDefinition> listarActivos();

    /** Para administración del catálogo: incluye inactivos. */
    List<ActivityTypeDefinition> listarTodos();
}

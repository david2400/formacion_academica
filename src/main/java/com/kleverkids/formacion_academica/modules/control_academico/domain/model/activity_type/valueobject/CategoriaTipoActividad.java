package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_type.valueobject;

/**
 * Agrupación visual/funcional de un tipo de actividad en el registro
 * extensible {@code ActivityTypeDefinition} (ver arquitectura aprobada,
 * sección "ActivityTypeDefinition"). Se usa para organizar el panel
 * "+ Agregar actividad" del builder (Etapa 4), no para lógica de negocio.
 */
public enum CategoriaTipoActividad {
    CONTENIDO,
    MULTIMEDIA,
    INTERACTIVO,
    EVALUACION,
    TAREAS,
    EXTERNO
}

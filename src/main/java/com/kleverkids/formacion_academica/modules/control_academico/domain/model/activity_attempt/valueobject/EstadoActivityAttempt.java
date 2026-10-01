package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.valueobject;

/** Estados de un intento. A diferencia de Activity/LearningSequence (ciclo
 * editorial), estos sí son hechos históricos reales y se persisten todos
 * (ver arquitectura aprobada §2.4). */
public enum EstadoActivityAttempt {
    IN_PROGRESS,
    COMPLETED,
    /** Expiró (venció la ventana de la asignación) sin que el estudiante lo completara. */
    ABANDONED
}

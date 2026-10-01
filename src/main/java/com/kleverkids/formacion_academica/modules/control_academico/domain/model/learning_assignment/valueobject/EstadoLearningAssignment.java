package com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject;

/**
 * Solo los estados que alguien efectivamente escribe se persisten:
 * ASSIGNED (default al crear), IN_PROGRESS (al primer intento), COMPLETED
 * (al llegar al 100% de progreso), CANCELLED (acción explícita). AVAILABLE y
 * OVERDUE son derivados de fecha (available_from/due_at vs. ahora) y se
 * calculan solo al consultar — igual que AVAILABLE/LOCKED/EXPIRED en Activity
 * (ver arquitectura aprobada §2.4): no hay @Scheduled en el proyecto para
 * mantenerlos sincronizados si se persistieran.
 */
public enum EstadoLearningAssignment {
    ASSIGNED,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}

package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_assignment.valueobject;

/**
 * Estado de una solicitud de feedback para UN estudiante y UNA clase.
 *
 * <p>Es un estado DERIVADO (ver {@code FeedbackAssignment#estadoSolicitud}),
 * nunca se persiste: depende del reloj, de la ventana y de si existe una
 * participación. Persistirlo exigiría un job que lo sincronice; es el mismo
 * criterio ya aplicado a AVAILABLE/OVERDUE en LearningAssignment. Los estados
 * que sí son ciclo de vida (formulario y aplicación) viven en el motor de
 * estados de access_control.
 */
public enum EstadoSolicitudFeedback {
    /** La ventana todavía no abre (la clase no ha terminado o hay offset). */
    PENDIENTE,
    /** Se puede responder ahora. */
    DISPONIBLE,
    /** El estudiante ya respondió (y no se permiten respuestas múltiples). */
    RESPONDIDO,
    /** La ventana cerró sin respuesta. */
    EXPIRADO,
    /** La aplicación fue cerrada o cancelada por un coordinador. */
    CERRADO
}

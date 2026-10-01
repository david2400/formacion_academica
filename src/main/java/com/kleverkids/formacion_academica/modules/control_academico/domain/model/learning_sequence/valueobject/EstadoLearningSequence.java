package com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject;

/** Ciclo de vida editorial de una LearningSequence. Igual que EstadoActivity: solo el
 * contenido editorial se persiste aquí; disponibilidad/vencimiento son derivados
 * de la asignación (Etapa 3), no de este enum. */
public enum EstadoLearningSequence {
    DRAFT,
    PUBLISHED,
    ARCHIVED
}

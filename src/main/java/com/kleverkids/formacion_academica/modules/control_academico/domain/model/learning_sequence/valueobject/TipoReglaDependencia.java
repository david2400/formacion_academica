package com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject;

/**
 * Tipo de regla de desbloqueo entre dos items de una secuencia (ver
 * arquitectura aprobada §2.5). Un enum pequeño y cerrado a nivel de "tipo de
 * regla" (no de actividad): agregar una regla nueva (ej. TIME_SPENT_GTE) no
 * toca el núcleo, solo valida/evalúa según corresponda cuando exista
 * ActivityAttempt (Etapa 3). En Etapa 2 estas reglas solo se definen (CRUD);
 * la evaluación en tiempo real llega con los intentos.
 */
public enum TipoReglaDependencia {
    /** El item del que depende debe estar completado (cualquier resultado). */
    COMPLETED,
    /** El item del que depende debe estar completado y aprobado. */
    PASSED,
    /** La nota del item del que depende debe ser >= ruleValorNumerico. */
    SCORE_GTE,
    /** El número de intentos del item del que depende debe ser >= ruleValorNumerico. */
    ATTEMPTS_GTE,
    /** La fecha actual debe ser posterior a ruleValorFecha. */
    DATE_AFTER
}

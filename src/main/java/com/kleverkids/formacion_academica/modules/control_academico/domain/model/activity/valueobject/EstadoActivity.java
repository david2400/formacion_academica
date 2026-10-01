package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject;

/**
 * Ciclo de vida editorial de una {@code Activity}. Son los únicos tres estados
 * que se persisten para la actividad en sí (ver arquitectura aprobada, FASE 2.4):
 * disponibilidad/bloqueo/vencimiento son derivados de fechas y asignación, no
 * columnas de este enum.
 */
public enum EstadoActivity {
    DRAFT,
    PUBLISHED,
    ARCHIVED
}

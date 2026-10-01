package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject;

/**
 * Qué tipo de contenido físico guarda un {@code ActivityContentItem}.
 * En Etapa 1 solo existen QUESTION (referencia a una Pregunta existente) y
 * TEXT (contenido enriquecido acotado). MEDIA y EXTERNAL se agregarán cuando
 * se implementen video/audio/embeds (Etapa 6 del roadmap aprobado) — añadir un
 * valor aquí no requiere migración de datos porque la columna es VARCHAR sin
 * CHECK constraint (ver nota en la entidad JPA).
 */
public enum ContentKind {
    QUESTION,
    TEXT
}

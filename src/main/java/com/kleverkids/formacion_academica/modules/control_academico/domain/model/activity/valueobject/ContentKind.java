package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject;

/**
 * Qué tipo de contenido físico guarda un {@code ActivityContentItem}.
 * QUESTION referencia una Pregunta existente, TEXT es contenido enriquecido
 * acotado, y EXTERNAL (Etapa 6-contenido-externo) es un recurso de terceros
 * (video de YouTube/Vimeo, un embed de Wordwall/Prezi/Genially/H5P, o un
 * enlace simple) — ver {@code ActivityContentItem#contenidoExterno}. MEDIA
 * (subir un archivo de audio/video propio, no un enlace externo) sigue
 * reservado para una etapa futura que todavía no se ha solicitado. Añadir un
 * valor aquí no requiere migración de datos porque la columna es VARCHAR sin
 * CHECK constraint (ver nota en la entidad JPA).
 */
public enum ContentKind {
    QUESTION,
    TEXT,
    EXTERNAL
}

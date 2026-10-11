package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.valueobject;

/**
 * Tipos de pregunta de un formulario de feedback.
 *
 * <p>Toda respuesta de cualquier tipo cabe en las mismas tres columnas de
 * {@code feedback_answers} (valor_numerico, option_id, texto), así que
 * agregar un tipo nuevo = un valor aquí + un {@code FeedbackQuestionTypeHandler},
 * sin migración de datos (la columna es VARCHAR sin CHECK, igual que
 * {@code ContentKind}).
 *
 * <p>{@link #cuantitativa} indica si el tipo aporta un valor numérico que
 * entra en promedios (la analítica filtra por esto, no por una lista de tipos
 * quemada en las consultas).
 */
public enum FeedbackQuestionType {
    /** Valor entero entre escalaMin y escalaMax (ej. 0..10). */
    NUMERIC_SCALE(true),
    /** Un nivel de una FeedbackScale reutilizable (ej. 1 Muy deficiente .. 5 Excelente). */
    LIKERT(true),
    /** Una opción propia de la pregunta. Cuantitativa solo si sus opciones tienen valor. */
    SINGLE_CHOICE(false),
    /** Varias opciones propias de la pregunta (una fila de respuesta por opción). */
    MULTIPLE_CHOICE(false),
    /** Texto libre (comentarios). */
    OPEN_TEXT(false),
    /** Sí = 1, No = 0: el promedio es directamente el % de "sí". */
    YES_NO(true);

    private final boolean cuantitativa;

    FeedbackQuestionType(boolean cuantitativa) {
        this.cuantitativa = cuantitativa;
    }

    public boolean isCuantitativa() {
        return cuantitativa;
    }
}

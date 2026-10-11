package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.valueobject.FeedbackQuestionType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Pregunta configurable de un formulario.
 *
 * <p>Qué campos aplican depende del tipo (lo valida su handler):
 * <ul>
 *   <li>LIKERT → {@link #scaleId} obligatorio.</li>
 *   <li>NUMERIC_SCALE → {@link #escalaMin}/{@link #escalaMax} (+ etiquetas opcionales de extremos).</li>
 *   <li>SINGLE_CHOICE / MULTIPLE_CHOICE → opciones propias (FeedbackQuestionOption).</li>
 *   <li>OPEN_TEXT / YES_NO → sin configuración adicional.</li>
 * </ul>
 * Se eligieron columnas tipadas y no un JSON de configuración porque son pocas,
 * estables y la analítica filtra por ellas.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackQuestion {

    private Long id;
    private Long formId;
    private Long sectionId;
    private FeedbackQuestionType tipo;
    private String enunciado;
    private String ayuda;
    private boolean obligatoria;
    private Integer orden;

    /** Escala reutilizable (solo LIKERT). */
    private Long scaleId;

    /** Criterio estable para comparar entre versiones de formularios y años (opcional). */
    private Long criterionId;

    private Integer escalaMin;
    private Integer escalaMax;
    private String etiquetaMin;
    private String etiquetaMax;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

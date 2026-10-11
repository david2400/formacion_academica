package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Respuesta a UNA pregunta. Normalizada (no JSON) para que promedios y
 * distribuciones se calculen con AVG/COUNT ... GROUP BY question_id en SQL.
 * MULTIPLE_CHOICE produce una fila por opción marcada.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackAnswer {

    private Long id;
    private Long responseId;
    private Long questionId;
    private Long optionId;
    private BigDecimal valorNumerico;

    /**
     * Valor llevado a 0..1 según la escala de la pregunta ((v - min) / (max - min)).
     * Se calcula al guardar porque el formulario publicado es inmutable, y
     * permite promediar preguntas con escalas distintas (1-5, 0-10, sí/no) sin
     * joins a la definición en cada reporte. NULL en respuestas no cuantitativas.
     */
    private BigDecimal valorNormalizado;

    private String texto;
}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.valueobject.FeedbackQuestionType;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Resultado agregado de una pregunta (solo con formulario o aplicación en el filtro). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoPreguntaFeedbackDto {

    private Long preguntaId;
    private Long seccionId;
    private String enunciado;
    private FeedbackQuestionType tipo;
    private Long criterionId;
    private long respuestas;
    /** Promedio en la escala propia de la pregunta (ej. 4.2 de 5). */
    private BigDecimal promedio;
    /** Promedio normalizado 0-100. */
    private BigDecimal indice;
    private boolean datosSuficientes;

    @Builder.Default
    private List<ItemDistribucionFeedbackDto> distribucion = new ArrayList<>();
}

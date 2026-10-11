package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Respuesta a una pregunta. Índices pensados para la analítica:
 * (question_id, valor_numerico) cubre AVG y distribución de escalas;
 * (question_id, option_id) cubre distribución de opciones.
 * Sin AuditInfo por la misma razón que FeedbackResponseEntity.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback_answers", indexes = {
        @Index(name = "idx_feedback_answers_response", columnList = "response_id"),
        @Index(name = "idx_feedback_answers_question_valor", columnList = "question_id, valor_numerico"),
        @Index(name = "idx_feedback_answers_question_option", columnList = "question_id, option_id")
})
public class FeedbackAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "response_id", nullable = false)
    private Long responseId;

    @Column(name = "question_id", nullable = false)
    private Long questionId;

    @Column(name = "option_id")
    private Long optionId;

    @Column(name = "valor_numerico", precision = 6, scale = 2)
    private BigDecimal valorNumerico;

    /** 0..1, ver FeedbackAnswer#valorNormalizado. */
    @Column(name = "valor_normalizado", precision = 5, scale = 4)
    private BigDecimal valorNormalizado;

    @Column(name = "texto", columnDefinition = "TEXT")
    private String texto;
}

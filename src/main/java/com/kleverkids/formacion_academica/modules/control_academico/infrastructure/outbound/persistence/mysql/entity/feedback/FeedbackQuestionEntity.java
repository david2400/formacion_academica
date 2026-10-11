package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form.valueobject.FeedbackQuestionType;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Pregunta. {@code form_id} se desnormaliza (además de section_id) porque la
 * analítica filtra preguntas por formulario sin pasar por secciones.
 * {@code tipo} es VARCHAR sin CHECK: agregar un tipo no exige migración.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "feedback_questions", indexes = {
        @Index(name = "idx_feedback_questions_section", columnList = "section_id, orden"),
        @Index(name = "idx_feedback_questions_form", columnList = "form_id"),
        @Index(name = "idx_feedback_questions_criterion", columnList = "criterion_id")
})
public class FeedbackQuestionEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form_id", nullable = false)
    private Long formId;

    @Column(name = "section_id", nullable = false)
    private Long sectionId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false, length = 30)
    private FeedbackQuestionType tipo;

    @Column(name = "enunciado", nullable = false, length = 500)
    private String enunciado;

    @Column(name = "ayuda", length = 500)
    private String ayuda;

    @Column(name = "obligatoria", nullable = false)
    private boolean obligatoria;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    @Column(name = "scale_id")
    private Long scaleId;

    @Column(name = "criterion_id")
    private Long criterionId;

    @Column(name = "escala_min")
    private Integer escalaMin;

    @Column(name = "escala_max")
    private Integer escalaMax;

    @Column(name = "etiqueta_min", length = 100)
    private String etiquetaMin;

    @Column(name = "etiqueta_max", length = 100)
    private String etiquetaMax;
}

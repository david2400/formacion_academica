package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Opción de una pregunta SINGLE_CHOICE / MULTIPLE_CHOICE. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackQuestionOption {

    private Long id;
    private Long questionId;
    private String etiqueta;

    /** Valor numérico opcional: si todas las opciones lo tienen, la pregunta entra en promedios. */
    private BigDecimal valor;

    private Integer orden;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

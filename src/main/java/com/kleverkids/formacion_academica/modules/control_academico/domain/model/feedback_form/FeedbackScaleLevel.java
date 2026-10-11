package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/** Un nivel de una escala: valor numérico + etiqueta (ej. 5 = Excelente). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackScaleLevel {

    private Long id;
    private Long scaleId;
    private BigDecimal valor;
    private String etiqueta;
    private Integer orden;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

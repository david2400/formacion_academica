package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Índice por criterio, comparable entre formularios y versiones. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoCriterioFeedbackDto {

    private Long criterionId;
    private String codigo;
    private String nombre;
    private long respuestas;
    private BigDecimal indice;
}

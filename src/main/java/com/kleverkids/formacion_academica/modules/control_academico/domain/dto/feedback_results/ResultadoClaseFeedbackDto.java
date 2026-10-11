package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Fila del ranking por clase (paginado). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResultadoClaseFeedbackDto {

    private Long assignmentId;
    private Long claseId;
    private String claseNombre;
    private LocalDateTime claseInicio;
    private Long grupoId;
    private long respuestas;
    private BigDecimal indice;
    private boolean datosSuficientes;
}

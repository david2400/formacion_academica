package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Cuántas veces se eligió un valor u opción. */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ItemDistribucionFeedbackDto {

    private String etiqueta;
    private BigDecimal valor;
    private Long opcionId;
    private long cantidad;
}

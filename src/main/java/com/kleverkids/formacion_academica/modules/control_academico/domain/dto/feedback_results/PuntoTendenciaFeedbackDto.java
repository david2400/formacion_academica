package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/** Un punto de la serie histórica (periodo = "2026-10" o "2026-W42"). */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PuntoTendenciaFeedbackDto {

    private String periodo;
    private long respuestas;
    private BigDecimal indice;
    private boolean datosSuficientes;
}

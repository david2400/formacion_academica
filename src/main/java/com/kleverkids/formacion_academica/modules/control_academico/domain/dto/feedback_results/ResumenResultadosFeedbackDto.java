package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.feedback_results;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * KPIs del filtro. {@code indice} es el promedio de respuestas cuantitativas
 * normalizadas a 0-100 (comparable entre escalas distintas). Si el filtro
 * incluye formularios anónimos y hay menos respuestas que el umbral, los
 * valores derivados vienen en null y {@code datosSuficientes=false}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResumenResultadosFeedbackDto {

    private long respuestas;
    private long participaciones;
    private long elegibles;
    private BigDecimal participacionPct;
    private BigDecimal indice;
    private boolean datosSuficientes;
    private int umbralAplicado;
}

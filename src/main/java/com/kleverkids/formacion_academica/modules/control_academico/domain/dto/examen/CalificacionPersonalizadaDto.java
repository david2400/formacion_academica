package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
public class CalificacionPersonalizadaDto {
    private Long examenId;
    private Long estudianteId;
    private String criterio;
    private BigDecimal puntajeOtorgado;

public CalificacionPersonalizadaDto(Long examenId, Long estudianteId, String criterio, BigDecimal puntajeOtorgado) {
        if (examenId == null) {
            throw new IllegalArgumentException("El examen es obligatorio");
        }
        if (estudianteId == null) {
            throw new IllegalArgumentException("El estudiante es obligatorio");
        }
        if (criterio == null || criterio.isBlank()) {
            throw new IllegalArgumentException("El criterio es obligatorio");
        }
        if (puntajeOtorgado == null || BigDecimal.ZERO.compareTo(puntajeOtorgado) > 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
        this.examenId = examenId;
        this.estudianteId = estudianteId;
        this.criterio = criterio;
        this.puntajeOtorgado = puntajeOtorgado;
    }

}

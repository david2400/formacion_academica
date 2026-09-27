package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_pregunta;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenPreguntaDto {
    private Long id;
    private Long examenId;
    private Long preguntaId;
    private Integer orden;
    private BigDecimal puntos;
}

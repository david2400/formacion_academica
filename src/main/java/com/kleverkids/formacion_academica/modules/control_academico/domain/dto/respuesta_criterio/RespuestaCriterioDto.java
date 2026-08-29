package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RespuestaCriterioDto {
    private Long id;
    private Long estudianteExamenId;
    private Long examenId;
    private Long criterioId;
    private Long estudianteId;
    private String respuesta;
    private BigDecimal puntajeObtenido;
    private LocalDateTime registradaEn;

}

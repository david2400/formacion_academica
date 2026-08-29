package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen_criterio;

import java.math.BigDecimal;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamenCriterio {
    private Long id;
    private Long examenId;
    private Long criterioId;
    private BigDecimal ponderacion;
    private Integer orden;

}

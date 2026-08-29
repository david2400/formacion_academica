package com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ReglaCalificacion {
    private String criterio;
    private BigDecimal ponderacion;
    private BigDecimal puntajeMaximo;

}

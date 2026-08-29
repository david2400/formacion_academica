package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ObservacionCriterioDto {
    private Long id;
    private Long examenId;
    private Long criterioId;
    private Long estudianteId;
    private BigDecimal puntaje;
    private String observacion;
    private String recomendacion;

}

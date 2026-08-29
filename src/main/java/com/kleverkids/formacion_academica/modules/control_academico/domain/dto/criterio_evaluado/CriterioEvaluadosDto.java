package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.criterio_evaluado;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CriterioEvaluadosDto {
    private Long id;
    private String nombre;
    private String descripcion;
    private String recomendacionBase;

}

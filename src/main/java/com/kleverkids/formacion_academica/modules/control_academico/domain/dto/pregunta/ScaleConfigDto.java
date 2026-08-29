package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScaleConfigDto {
    private Integer minValue;
    private Integer maxValue;
    private String minLabel;
    private String maxLabel;
    private List<String> labels;

}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.List;

public record ScaleConfigDto(
    Integer minValue,
    Integer maxValue,
    String minLabel,
    String maxLabel,
    List<String> labels
) {}

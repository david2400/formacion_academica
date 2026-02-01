package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.List;

public record ScaleConfigDto(
    int minValue,
    int maxValue,
    String minLabel,
    String maxLabel,
    List<String> labels
) {}

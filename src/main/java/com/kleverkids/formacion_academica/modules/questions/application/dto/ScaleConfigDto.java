package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.util.List;

public record ScaleConfigDto(
    int minValue,
    int maxValue,
    String minLabel,
    String maxLabel,
    List<String> labels
) {}

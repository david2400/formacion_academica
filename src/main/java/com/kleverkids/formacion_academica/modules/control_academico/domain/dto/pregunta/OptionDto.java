package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

public record OptionDto(
    Long id,
    String text,
    MediaDto media,
    boolean isCorrect
) {}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.UUID;

public record OptionDto(
    UUID id,
    String text,
    MediaDto media,
    boolean isCorrect
) {}

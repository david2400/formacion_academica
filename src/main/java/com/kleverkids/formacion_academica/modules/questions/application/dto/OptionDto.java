package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.util.UUID;

public record OptionDto(
    UUID id,
    String text,
    MediaDto media,
    boolean isCorrect
) {}

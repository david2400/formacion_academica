package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.util.UUID;

public record OrderingItemDto(
    UUID id,
    String text,
    int correctPosition,
    MediaDto media
) {}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.UUID;

public record OrderingItemDto(
    UUID id,
    String text,
    int correctPosition,
    MediaDto media
) {}

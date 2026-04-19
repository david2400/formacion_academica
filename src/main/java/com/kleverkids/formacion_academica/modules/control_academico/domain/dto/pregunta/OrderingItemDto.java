package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

public record OrderingItemDto(
    Long id,
    String text,
    Integer correctPosition,
    MediaDto media
) {}

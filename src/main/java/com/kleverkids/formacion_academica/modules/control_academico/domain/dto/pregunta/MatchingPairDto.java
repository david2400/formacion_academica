package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

public record MatchingPairDto(
    Long id,
    String leftItem,
    String rightItem,
    MediaDto leftMedia,
    MediaDto rightMedia
) {}

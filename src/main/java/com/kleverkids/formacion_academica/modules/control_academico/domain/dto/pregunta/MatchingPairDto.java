package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.UUID;

public record MatchingPairDto(
    UUID id,
    String leftItem,
    String rightItem,
    MediaDto leftMedia,
    MediaDto rightMedia
) {}

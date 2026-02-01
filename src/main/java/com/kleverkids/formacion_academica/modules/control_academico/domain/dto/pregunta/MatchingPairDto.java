package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.util.UUID;

public record MatchingPairDto(
    UUID id,
    String leftItem,
    String rightItem,
    MediaDto leftMedia,
    MediaDto rightMedia
) {}

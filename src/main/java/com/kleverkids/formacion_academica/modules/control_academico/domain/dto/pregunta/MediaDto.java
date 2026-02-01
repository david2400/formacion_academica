package com.kleverkids.formacion_academica.modules.questions.application.dto;

import java.util.UUID;

public record MediaDto(
    UUID id,
    String type,
    String url,
    String altText
) {}

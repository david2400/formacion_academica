package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import java.util.UUID;

public record MediaDto(
    UUID id,
    String type,
    String url,
    String altText
) {}

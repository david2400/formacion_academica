package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.time.Instant;
import java.util.List;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record PreguntaDto(
    Long id,
    String questionType,
    String questionText,
    String difficulty,
    Integer maxScore,
    Long themeId,
    List<MediaDto> media,
    String hint,
    String explanation,
    List<String> tags,
    Map<String, Object> metadata,
    Map<String, Object> specificData,
    Instant createdAt,
    Instant updatedAt,
    Integer version
) {}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public record QuestionAnswerDto(
    UUID questionId,
    UUID selectedOptionId,
    List<UUID> selectedOptionIds,
    Boolean booleanAnswer,
    String textAnswer,
    BigDecimal numericAnswer,
    Integer scaleValue,
    List<UUID> orderedItemIds,
    Map<UUID, UUID> matchedPairs
) {}

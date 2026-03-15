package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


public record QuestionAnswerDto(
    Long questionId,
    Long selectedOptionId,
    List<Long> selectedOptionIds,
    Boolean booleanAnswer,
    String textAnswer,
    BigDecimal numericAnswer,
    Integer scaleValue,
    List<Long> orderedItemIds,
    Map<Long, Long> matchedPairs
) {}

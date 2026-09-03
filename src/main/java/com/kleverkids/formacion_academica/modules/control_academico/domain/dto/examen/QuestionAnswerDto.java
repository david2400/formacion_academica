package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.Map;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerDto {
    private Long questionId;
        private String selectedOptionId;
    private List<String> selectedOptionIds;
    private Boolean booleanAnswer;
    private String textAnswer;
    private BigDecimal numericAnswer;
    private Integer scaleValue;
    private List<String> orderedItemIds;
    private Map<String, String> matchedPairs;

}

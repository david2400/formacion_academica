package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamQuestionDto {
    private Long id;
    private Long questionId;
    private int order;
    private BigDecimal points;
    private boolean required;

}

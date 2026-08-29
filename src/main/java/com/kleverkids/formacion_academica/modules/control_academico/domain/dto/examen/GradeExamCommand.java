package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;
import java.math.BigDecimal;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GradeExamCommand {
    private Long gradedBy;
    private List<QuestionGradeDto> grades;

@Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionGradeDto {
        private Long questionId;
        private BigDecimal score;
        private String feedback;
    }

}

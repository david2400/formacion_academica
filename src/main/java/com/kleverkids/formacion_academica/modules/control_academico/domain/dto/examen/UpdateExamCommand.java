package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.util.List;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateExamCommand {
    private String name;
    private String code;
    private String subject;
    private String gradeLevel;
    private String instructions;
    private TimeConfigDto timeConfig;
    private List<ExamQuestionDto> questions;
    private List<EvaluationCriteriaDto> criteria;

}

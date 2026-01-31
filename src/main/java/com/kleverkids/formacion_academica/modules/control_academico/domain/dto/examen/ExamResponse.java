package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.Exam;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.ExamQuestion;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.EvaluationCriteria;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.TimeConfig;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record ExamResponse(
    UUID id,
    String name,
    String code,
    String subject,
    String gradeLevel,
    String instructions,
    String status,
    TimeConfigDto timeConfig,
    List<ExamQuestionDto> questions,
    List<EvaluationCriteriaDto> criteria,
    BigDecimal totalPoints,
    Instant createdAt,
    Instant updatedAt
) {
    public static ExamResponse fromDomain(Exam exam) {
        return new ExamResponse(
            exam.getId(),
            exam.getName(),
            exam.getCode(),
            exam.getSubject(),
            exam.getGradeLevel(),
            exam.getInstructions(),
            exam.getStatus().getValue(),
            mapTimeConfig(exam.getTimeConfig()),
            mapQuestions(exam.getQuestions()),
            mapCriteria(exam.getCriteria()),
            exam.getTotalPoints(),
            exam.getCreatedAt(),
            exam.getUpdatedAt()
        );
    }
    
    private static TimeConfigDto mapTimeConfig(TimeConfig tc) {
        if (tc == null) return null;
        return new TimeConfigDto(tc.getDuration(), tc.getScheduledDate(), tc.getStartTime(), tc.getEndTime());
    }
    
    private static List<ExamQuestionDto> mapQuestions(List<ExamQuestion> questions) {
        if (questions == null) return null;
        return questions.stream()
            .map(q -> new ExamQuestionDto(q.getId(), q.getQuestionId(), q.getOrder(), q.getPoints(), q.isRequired()))
            .collect(Collectors.toList());
    }
    
    private static List<EvaluationCriteriaDto> mapCriteria(List<EvaluationCriteria> criteria) {
        if (criteria == null) return null;
        return criteria.stream()
            .map(c -> new EvaluationCriteriaDto(c.getId(), c.getName(), c.getDescription(), c.getWeight(), c.getMaxScore()))
            .collect(Collectors.toList());
    }
}

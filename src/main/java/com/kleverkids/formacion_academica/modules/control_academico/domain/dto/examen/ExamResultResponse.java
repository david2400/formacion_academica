package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamResult;

public record ExamResultResponse(
    UUID id,
    UUID examId,
    UUID studentId,
    UUID submissionId,
    BigDecimal totalScore,
    BigDecimal maxScore,
    BigDecimal percentage,
    String grade,
    List<QuestionResultDto> questionResults,
    Instant gradedAt,
    UUID gradedBy
) {
    public static ExamResultResponse fromDomain(ExamResult result) {
        return new ExamResultResponse(
            result.getId(),
            result.getExamId(),
            result.getStudentId(),
            result.getSubmissionId(),
            result.getTotalScore(),
            result.getMaxScore(),
            result.getPercentage(),
            result.getGrade(),
            result.getQuestionResults() != null ? result.getQuestionResults().stream()
                .map(qr -> new QuestionResultDto(qr.getQuestionId(), qr.getScore(), qr.getMaxScore(), qr.isCorrect(), qr.getFeedback()))
                .collect(Collectors.toList()) : null,
            result.getGradedAt(),
            result.getGradedBy()
        );
    }
    
    public record QuestionResultDto(
        UUID questionId,
        BigDecimal score,
        BigDecimal maxScore,
        boolean correct,
        String feedback
    ) {}
}

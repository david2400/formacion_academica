package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ResultadoExamen;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;

import java.util.stream.Collectors;


public record ExamResultResponse(
    Long id,
    Long examId,
    Long studentId,
    Long submissionId,
    BigDecimal totalScore,
    BigDecimal maxScore,
    BigDecimal percentage,
    String grade,
    List<QuestionResultDto> questionResults,
    Instant gradedAt,
    Long gradedBy
) {
    public static ExamResultResponse fromDomain(ResultadoExamen result) {
        return new ExamResultResponse(
            result.getId(),
            result.getExamenId(),
            result.getEstudianteId(),
            result.getEnvioId(),
            result.getPuntajeTotal(),
            result.getPuntajeMaximo(),
            result.getPorcentaje(),
            result.getCalificacion(),
            result.getResultadosPreguntas() != null ? result.getResultadosPreguntas().stream()
                .map(qr -> new QuestionResultDto(qr.getPreguntaId(), qr.getPuntaje(), qr.getPuntajeMaximo(),
                        qr.isCorrecto(), qr.getRetroalimentacion()))
                .collect(Collectors.toList()) : null,
            result.getCalificadoEn(),
            result.getCalificadoPor()
        );
    }
    
    public record QuestionResultDto(
        Long questionId,
        BigDecimal score,
        BigDecimal maxScore,
        boolean correct,
        String feedback
    ) {}
}

package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;
import java.math.BigDecimal;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ResultadoExamen;

import lombok.experimental.Accessors;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ExamResultResponse {
    private Long id;
    private Long examId;
    private Long studentId;
    private Long submissionId;
    private BigDecimal totalScore;
    private BigDecimal maxScore;
    private BigDecimal percentage;
    private String grade;
    private List<QuestionResultDto> questionResults;
    private Instant gradedAt;
    private Long gradedBy;

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
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class QuestionResultDto {
        private Long questionId;
        private BigDecimal score;
        private BigDecimal maxScore;
        private boolean correct;
        private String feedback;
    }

}

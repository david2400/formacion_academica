package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamResult;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamSubmission;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.QuestionAnswer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class ExamScoringService {
    
    public ExamResult calculateResult(UUID examId, ExamSubmission submission, BigDecimal maxScore) {
        List<ExamResult.QuestionResult> questionResults = new ArrayList<>();
        BigDecimal totalScore = BigDecimal.ZERO;
        
        for (QuestionAnswer answer : submission.getAnswers()) {
            if (answer.isGraded()) {
                totalScore = totalScore.add(answer.getScore() != null ? answer.getScore() : BigDecimal.ZERO);
                
                questionResults.add(new ExamResult.QuestionResult(
                    answer.getQuestionId(),
                    answer.getScore(),
                    BigDecimal.ZERO, // Would need question max score
                    answer.getScore() != null && answer.getScore().compareTo(BigDecimal.ZERO) > 0,
                    answer.getFeedback()
                ));
            }
        }
        
        return new ExamResult(
            null,
            examId,
            submission.getStudentId(),
            submission.getId(),
            totalScore,
            maxScore,
            questionResults
        );
    }
    
    public String calculateGrade(BigDecimal percentage) {
        if (percentage == null) return "F";
        
        double pct = percentage.doubleValue();
        if (pct >= 90) return "A";
        if (pct >= 80) return "B";
        if (pct >= 70) return "C";
        if (pct >= 60) return "D";
        return "F";
    }
}

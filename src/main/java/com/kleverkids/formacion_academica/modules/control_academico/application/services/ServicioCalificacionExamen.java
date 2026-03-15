package com.kleverkids.formacion_academica.modules.control_academico.application.services;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamResult;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.examen.ExamSubmission;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.respuesta_pregunta.QuestionAnswer;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ServicioCalificacionExamen {
    
    public ExamResult calculateResult(Long examId, ExamSubmission submission, BigDecimal maxScore) {
        List<ExamResult.QuestionResult> questionResults = new ArrayList<>();
        
        BigDecimal totalScore = BigDecimal.ZERO;
        
        for (QuestionAnswer answer : submission.getAnswers()) {
            BigDecimal questionScore = calculateQuestionScore(answer);
            totalScore = totalScore.add(questionScore);
            
            boolean isCorrect = questionScore.compareTo(BigDecimal.ZERO) > 0;
            BigDecimal questionMaxScore = getQuestionMaxScore(answer);
            
            questionResults.add(new ExamResult.QuestionResult(
                answer.getQuestionId(),
                questionScore,
                questionMaxScore,
                isCorrect,
                answer.getFeedback()
            ));
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
        if (percentage.compareTo(new BigDecimal("90")) >= 0) return "A";
        if (percentage.compareTo(new BigDecimal("80")) >= 0) return "B";
        if (percentage.compareTo(new BigDecimal("70")) >= 0) return "C";
        if (percentage.compareTo(new BigDecimal("60")) >= 0) return "D";
        return "F";
    }
    
    private BigDecimal calculateQuestionScore(QuestionAnswer answer) {
        if (answer.getScore() != null) {
            return answer.getScore();
        }
        
        // Default scoring logic - if answer has a positive score, it's correct
        return answer.getScore() != null && answer.getScore().compareTo(BigDecimal.ZERO) > 0 
            ? answer.getScore() 
            : BigDecimal.ZERO;
    }
    
    private BigDecimal getQuestionMaxScore(QuestionAnswer answer) {
        // For now, return a default max score of 1 point per question
        // This could be enhanced to get the actual max score from the question
        return BigDecimal.ONE;
    }
    
    private BigDecimal calculatePercentage(BigDecimal score, BigDecimal maxScore) {
        if (maxScore.compareTo(BigDecimal.ZERO) == 0) {
            return BigDecimal.ZERO;
        }
        return score.multiply(new BigDecimal("100"))
                   .divide(maxScore, 2, RoundingMode.HALF_UP);
    }
}

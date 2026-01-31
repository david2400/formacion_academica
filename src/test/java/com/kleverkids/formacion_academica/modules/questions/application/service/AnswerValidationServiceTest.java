package com.kleverkids.formacion_academica.modules.questions.application.service;

import com.kleverkids.formacion_academica.modules.questions.application.dto.AnswerValidationRequest;
import com.kleverkids.formacion_academica.modules.questions.application.dto.ValidationResult;
import com.kleverkids.formacion_academica.modules.questions.domain.model.*;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.Difficulty;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.Option;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class AnswerValidationServiceTest {
    
    private AnswerValidationService validationService;
    
    @BeforeEach
    void setUp() {
        validationService = new AnswerValidationService();
    }
    
    @Test
    void validateMultipleChoiceSingle_correctAnswer_returnsCorrect() {
        UUID correctOptionId = UUID.randomUUID();
        
        MultipleChoiceSingleQuestion question = new MultipleChoiceSingleQuestion();
        question.setId(UUID.randomUUID());
        question.setQuestionText("What is 2+2?");
        question.setDifficulty(Difficulty.BASIC);
        question.setMaxScore(10);
        question.setCorrectOptionId(correctOptionId);
        question.setOptions(List.of(
            Option.create(correctOptionId, "4", null, true),
            Option.create(UUID.randomUUID(), "5", null, false)
        ));
        
        AnswerValidationRequest request = new AnswerValidationRequest(
            correctOptionId, null, null, null, null, null, null, null
        );
        
        ValidationResult result = validationService.validate(question, request);
        
        assertTrue(result.isCorrect());
        assertEquals(BigDecimal.valueOf(10), result.score());
    }
    
    @Test
    void validateMultipleChoiceSingle_incorrectAnswer_returnsIncorrect() {
        UUID correctOptionId = UUID.randomUUID();
        UUID wrongOptionId = UUID.randomUUID();
        
        MultipleChoiceSingleQuestion question = new MultipleChoiceSingleQuestion();
        question.setId(UUID.randomUUID());
        question.setQuestionText("What is 2+2?");
        question.setDifficulty(Difficulty.BASIC);
        question.setMaxScore(10);
        question.setCorrectOptionId(correctOptionId);
        
        AnswerValidationRequest request = new AnswerValidationRequest(
            wrongOptionId, null, null, null, null, null, null, null
        );
        
        ValidationResult result = validationService.validate(question, request);
        
        assertFalse(result.isCorrect());
        assertEquals(BigDecimal.ZERO, result.score());
    }
    
    @Test
    void validateTrueFalse_correctAnswer_returnsCorrect() {
        TrueFalseQuestion question = new TrueFalseQuestion();
        question.setId(UUID.randomUUID());
        question.setQuestionText("The sky is blue");
        question.setDifficulty(Difficulty.BASIC);
        question.setMaxScore(5);
        question.setCorrectAnswer(true);
        
        AnswerValidationRequest request = new AnswerValidationRequest(
            null, null, true, null, null, null, null, null
        );
        
        ValidationResult result = validationService.validate(question, request);
        
        assertTrue(result.isCorrect());
        assertEquals(BigDecimal.valueOf(5), result.score());
    }
    
    @Test
    void validateNumeric_withinTolerance_returnsCorrect() {
        NumericQuestion question = new NumericQuestion();
        question.setId(UUID.randomUUID());
        question.setQuestionText("What is pi to 2 decimal places?");
        question.setDifficulty(Difficulty.INTERMEDIATE);
        question.setMaxScore(10);
        question.setCorrectValue(new BigDecimal("3.14"));
        question.setTolerance(new BigDecimal("0.01"));
        
        AnswerValidationRequest request = new AnswerValidationRequest(
            null, null, null, null, new BigDecimal("3.14"), null, null, null
        );
        
        ValidationResult result = validationService.validate(question, request);
        
        assertTrue(result.isCorrect());
        assertEquals(BigDecimal.valueOf(10), result.score());
    }
    
    @Test
    void validateNumeric_outsideTolerance_returnsIncorrect() {
        NumericQuestion question = new NumericQuestion();
        question.setId(UUID.randomUUID());
        question.setQuestionText("What is pi to 2 decimal places?");
        question.setDifficulty(Difficulty.INTERMEDIATE);
        question.setMaxScore(10);
        question.setCorrectValue(new BigDecimal("3.14"));
        question.setTolerance(new BigDecimal("0.01"));
        
        AnswerValidationRequest request = new AnswerValidationRequest(
            null, null, null, null, new BigDecimal("3.5"), null, null, null
        );
        
        ValidationResult result = validationService.validate(question, request);
        
        assertFalse(result.isCorrect());
        assertEquals(BigDecimal.ZERO, result.score());
    }
    
    @Test
    void validateOpenShort_exactMatch_returnsCorrect() {
        OpenShortQuestion question = new OpenShortQuestion();
        question.setId(UUID.randomUUID());
        question.setQuestionText("What is the capital of France?");
        question.setDifficulty(Difficulty.BASIC);
        question.setMaxScore(5);
        question.setAcceptedAnswers(List.of("Paris", "paris"));
        question.setCaseSensitive(false);
        
        AnswerValidationRequest request = new AnswerValidationRequest(
            null, null, null, "PARIS", null, null, null, null
        );
        
        ValidationResult result = validationService.validate(question, request);
        
        assertTrue(result.isCorrect());
        assertEquals(BigDecimal.valueOf(5), result.score());
    }
    
    @Test
    void validateOpenLong_requiresManualGrading() {
        OpenLongQuestion question = new OpenLongQuestion();
        question.setId(UUID.randomUUID());
        question.setQuestionText("Explain the theory of relativity");
        question.setDifficulty(Difficulty.ADVANCED);
        question.setMaxScore(20);
        
        AnswerValidationRequest request = new AnswerValidationRequest(
            null, null, null, "Einstein's theory states...", null, null, null, null
        );
        
        ValidationResult result = validationService.validate(question, request);
        
        assertTrue(result.requiresManualGrading());
        assertNull(result.score());
    }
}

package com.kleverkids.formacion_academica.modules.questions.application.service;

import com.kleverkids.formacion_academica.modules.questions.application.dto.AnswerValidationRequest;
import com.kleverkids.formacion_academica.modules.questions.application.dto.ValidationResult;
import com.kleverkids.formacion_academica.modules.questions.domain.model.*;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.MatchingPair;
import com.kleverkids.formacion_academica.modules.questions.domain.valueobject.OrderingItem;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class AnswerValidationService {
    
    public ValidationResult validate(Question question, AnswerValidationRequest request) {
        BigDecimal maxScore = BigDecimal.valueOf(question.getMaxScore());
        
        return switch (question) {
            case MultipleChoiceSingleQuestion q -> validateMultipleChoiceSingle(q, request, maxScore);
            case MultipleChoiceMultiQuestion q -> validateMultipleChoiceMulti(q, request, maxScore);
            case TrueFalseQuestion q -> validateTrueFalse(q, request, maxScore);
            case OpenShortQuestion q -> validateOpenShort(q, request, maxScore);
            case OpenLongQuestion q -> ValidationResult.requiresManualGrading(maxScore);
            case NumericQuestion q -> validateNumeric(q, request, maxScore);
            case ScaleQuestion q -> validateScale(q, request, maxScore);
            case OrderingQuestion q -> validateOrdering(q, request, maxScore);
            case MatchingQuestion q -> validateMatching(q, request, maxScore);
            default -> ValidationResult.requiresManualGrading(maxScore);
        };
    }
    
    private ValidationResult validateMultipleChoiceSingle(MultipleChoiceSingleQuestion q, 
            AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.selectedOptionId() == null) {
            return ValidationResult.incorrect(maxScore);
        }
        boolean isCorrect = q.getCorrectOptionId().equals(request.selectedOptionId());
        return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(maxScore);
    }
    
    private ValidationResult validateMultipleChoiceMulti(MultipleChoiceMultiQuestion q,
            AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.selectedOptionIds() == null || request.selectedOptionIds().isEmpty()) {
            return ValidationResult.incorrect(maxScore);
        }
        
        Set<UUID> correctIds = new HashSet<>(q.getCorrectOptionIds());
        Set<UUID> selectedIds = new HashSet<>(request.selectedOptionIds());
        
        if (correctIds.equals(selectedIds)) {
            return ValidationResult.correct(maxScore);
        }
        
        // Partial credit calculation
        long correctSelected = selectedIds.stream().filter(correctIds::contains).count();
        long incorrectSelected = selectedIds.size() - correctSelected;
        
        if (correctSelected == 0 || incorrectSelected >= correctSelected) {
            return ValidationResult.incorrect(maxScore);
        }
        
        BigDecimal partialScore = maxScore
            .multiply(BigDecimal.valueOf(correctSelected))
            .divide(BigDecimal.valueOf(correctIds.size()), 2, java.math.RoundingMode.HALF_UP);
        
        return ValidationResult.partial(partialScore, maxScore);
    }
    
    private ValidationResult validateTrueFalse(TrueFalseQuestion q, 
            AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.booleanAnswer() == null) {
            return ValidationResult.incorrect(maxScore);
        }
        boolean isCorrect = q.isCorrectAnswer() == request.booleanAnswer();
        return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(maxScore);
    }
    
    private ValidationResult validateOpenShort(OpenShortQuestion q,
            AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.textAnswer() == null || request.textAnswer().isBlank()) {
            return ValidationResult.incorrect(maxScore);
        }
        
        if (q.getAcceptedAnswers() == null || q.getAcceptedAnswers().isEmpty()) {
            return ValidationResult.requiresManualGrading(maxScore);
        }
        
        String answer = request.textAnswer().trim();
        boolean isCorrect = q.getAcceptedAnswers().stream()
            .anyMatch(accepted -> q.isCaseSensitive() 
                ? accepted.equals(answer) 
                : accepted.equalsIgnoreCase(answer));
        
        return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(maxScore);
    }
    
    private ValidationResult validateNumeric(NumericQuestion q,
            AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.numericAnswer() == null) {
            return ValidationResult.incorrect(maxScore);
        }
        
        BigDecimal tolerance = q.getTolerance() != null ? q.getTolerance() : BigDecimal.ZERO;
        BigDecimal diff = q.getCorrectValue().subtract(request.numericAnswer()).abs();
        
        boolean isCorrect = diff.compareTo(tolerance) <= 0;
        return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(maxScore);
    }
    
    private ValidationResult validateScale(ScaleQuestion q,
            AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.scaleValue() == null) {
            return ValidationResult.incorrect(maxScore);
        }
        
        int value = request.scaleValue();
        if (value < q.getScaleConfig().getMinValue() || value > q.getScaleConfig().getMaxValue()) {
            return ValidationResult.incorrect(maxScore);
        }
        
        if (q.getExpectedValue() != null) {
            boolean isCorrect = q.getExpectedValue().equals(value);
            return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(maxScore);
        }
        
        // Scale questions without expected value are typically surveys
        return ValidationResult.correct(maxScore);
    }
    
    private ValidationResult validateOrdering(OrderingQuestion q,
            AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.orderedItemIds() == null || request.orderedItemIds().isEmpty()) {
            return ValidationResult.incorrect(maxScore);
        }
        
        List<UUID> correctOrder = q.getItems().stream()
            .sorted(Comparator.comparingInt(OrderingItem::getCorrectPosition))
            .map(OrderingItem::getId)
            .toList();
        
        if (correctOrder.equals(request.orderedItemIds())) {
            return ValidationResult.correct(maxScore);
        }
        
        if (q.isPartialCredit()) {
            int correctPositions = 0;
            for (int i = 0; i < Math.min(correctOrder.size(), request.orderedItemIds().size()); i++) {
                if (correctOrder.get(i).equals(request.orderedItemIds().get(i))) {
                    correctPositions++;
                }
            }
            
            if (correctPositions > 0) {
                BigDecimal partialScore = maxScore
                    .multiply(BigDecimal.valueOf(correctPositions))
                    .divide(BigDecimal.valueOf(correctOrder.size()), 2, java.math.RoundingMode.HALF_UP);
                return ValidationResult.partial(partialScore, maxScore);
            }
        }
        
        return ValidationResult.incorrect(maxScore);
    }
    
    private ValidationResult validateMatching(MatchingQuestion q,
            AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.matchedPairs() == null || request.matchedPairs().isEmpty()) {
            return ValidationResult.incorrect(maxScore);
        }
        
        Map<UUID, UUID> correctPairs = new HashMap<>();
        for (MatchingPair pair : q.getPairs()) {
            correctPairs.put(pair.getId(), pair.getId());
        }
        
        int correctMatches = 0;
        for (Map.Entry<UUID, UUID> entry : request.matchedPairs().entrySet()) {
            if (entry.getValue() != null && entry.getValue().equals(correctPairs.get(entry.getKey()))) {
                correctMatches++;
            }
        }
        
        if (correctMatches == q.getPairs().size()) {
            return ValidationResult.correct(maxScore);
        }
        
        if (q.isPartialCredit() && correctMatches > 0) {
            BigDecimal partialScore = maxScore
                .multiply(BigDecimal.valueOf(correctMatches))
                .divide(BigDecimal.valueOf(q.getPairs().size()), 2, java.math.RoundingMode.HALF_UP);
            return ValidationResult.partial(partialScore, maxScore);
        }
        
        return ValidationResult.incorrect(maxScore);
    }
}

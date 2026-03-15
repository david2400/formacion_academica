package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.AnswerValidationRequest;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ValidationResult;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.MatchingPair;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.OrderingItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ServicioValidacionRespuesta {
    
    public ValidationResult validate(Question question, AnswerValidationRequest request) {
        BigDecimal maxScore = BigDecimal.valueOf(question.getMaxScore());
        
        switch (question.getQuestionType()) {
            case MULTIPLE_CHOICE_SINGLE:
                return validateMultipleChoiceSingle(question, request, maxScore);
            case MULTIPLE_CHOICE_MULTI:
                return validateMultipleChoiceMulti(question, request, maxScore);
            case TRUE_FALSE:
                return validateTrueFalse(question, request, maxScore);
            case OPEN_SHORT:
                return validateOpenShort(question, request, maxScore);
            case OPEN_LONG:
                return validateOpenLong(question, request, maxScore);
            case NUMERIC:
                return validateNumeric(question, request, maxScore);
            case SCALE:
                return validateScale(question, request, maxScore);
            case ORDERING:
                return validateOrdering(question, request, maxScore);
            case MATCHING:
                return validateMatching(question, request, maxScore);
            default:
                return ValidationResult.incorrect(BigDecimal.ZERO);
        }
    }
    
    private ValidationResult validateMultipleChoiceSingle(Question question, AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.selectedOptionId() == null) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof MultipleChoiceSingleQuestion) {
            MultipleChoiceSingleQuestion mcq = (MultipleChoiceSingleQuestion) question;
            boolean isCorrect = mcq.getCorrectOptionId().equals(request.selectedOptionId());
            return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateMultipleChoiceMulti(Question question, AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.selectedOptionIds() == null || request.selectedOptionIds().isEmpty()) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof MultipleChoiceMultiQuestion) {
            MultipleChoiceMultiQuestion mcq = (MultipleChoiceMultiQuestion) question;
            // Validación simple: si alguna opción seleccionada es correcta, damos puntos parciales
            boolean hasCorrectOption = mcq.getCorrectOptionIds().stream()
                .anyMatch(correctId -> request.selectedOptionIds().contains(correctId));
            
            if (hasCorrectOption) {
                return ValidationResult.correct(maxScore);
            }
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateTrueFalse(Question question, AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.booleanAnswer() == null) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof TrueFalseQuestion) {
            TrueFalseQuestion tfq = (TrueFalseQuestion) question;
            boolean isCorrect = tfq.isCorrectAnswer() == request.booleanAnswer();
            return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateOpenShort(Question question, AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.textAnswer() == null || request.textAnswer().trim().isEmpty()) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof OpenShortQuestion) {
            OpenShortQuestion osq = (OpenShortQuestion) question;
            List<String> correctAnswers = osq.getAcceptedAnswers();
            final String userAnswer = request.textAnswer().trim();
            if (!osq.isCaseSensitive()) {
                final String finalUserAnswer = userAnswer.toLowerCase();
                boolean isCorrect = correctAnswers.stream()
                    .anyMatch(correct -> {
                        String cmp = osq.isCaseSensitive() ? correct : correct.toLowerCase();
                        return cmp.equals(finalUserAnswer);
                    });
                return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
            } else {
                boolean isCorrect = correctAnswers.stream()
                    .anyMatch(userAnswer::equals);
                return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
            }
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateOpenLong(Question question, AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.textAnswer() == null || request.textAnswer().trim().isEmpty()) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        // Para ensayos, siempre se califica como correcto pero requiere revisión manual
        return ValidationResult.correct(maxScore);
    }
    
    private ValidationResult validateNumeric(Question question, AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.numericAnswer() == null) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof NumericQuestion) {
            NumericQuestion nq = (NumericQuestion) question;
            BigDecimal correctAnswer = nq.getCorrectValue();
            BigDecimal tolerance = nq.getTolerance() != null ? nq.getTolerance() : BigDecimal.ZERO;
            
            BigDecimal difference = request.numericAnswer().subtract(correctAnswer).abs();
            boolean isCorrect = difference.compareTo(tolerance) <= 0;
            
            return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateScale(Question question, AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.scaleValue() == null) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        // Para escalas, cualquier respuesta válida es aceptada
        return ValidationResult.correct(maxScore);
    }
    
    private ValidationResult validateOrdering(Question question, AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.orderedItemIds() == null || request.orderedItemIds().isEmpty()) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof OrderingQuestion) {
            OrderingQuestion oq = (OrderingQuestion) question;
            // Para ordenamiento, necesitamos extraer el orden correcto de los items
            List<Long> correctOrder = oq.getItems().stream()
                .filter(item -> item.getCorrectPosition() > 0)
                .sorted((a, b) -> Integer.compare(a.getCorrectPosition(), b.getCorrectPosition()))
                .map(OrderingItem::getId)
                .toList();
            List<Long> userOrder = request.orderedItemIds();
            
            if (correctOrder.equals(userOrder)) {
                return ValidationResult.correct(maxScore);
            }
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateMatching(Question question, AnswerValidationRequest request, BigDecimal maxScore) {
        if (request.matchedPairs() == null || request.matchedPairs().isEmpty()) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof MatchingQuestion) {
            MatchingQuestion mq = (MatchingQuestion) question;
            // Para matching, los pares correctos están en la lista pairs
            List<MatchingPair> correctPairs = mq.getPairs();
            
            // Convertir el Map de respuesta a lista de MatchingPair para comparación
            boolean allCorrect = request.matchedPairs().entrySet().stream()
                .allMatch(entry -> {
                    return correctPairs.stream()
                        .anyMatch(pair -> pair.getLeftItem().equals(entry.getKey()) && 
                                       pair.getRightItem().equals(entry.getValue()));
                });
            
            return allCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
}

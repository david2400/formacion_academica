package com.kleverkids.formacion_academica.modules.control_academico.application.services.pregunta;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.SolicitudValidacionRespuesta;
import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.pregunta.ValidationResult;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.pregunta.*;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.MatchingPair;
import com.kleverkids.formacion_academica.modules.control_academico.domain.valueobject.preguntas.OrderingItem;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ServicioValidacionRespuesta {
    
    public ValidationResult validate(Pregunta question, SolicitudValidacionRespuesta request) {
        BigDecimal maxScore = BigDecimal.valueOf(question.getPuntajeMaximo());
        
        switch (question.getTipoPregunta()) {
            case OPCION_MULTIPLE_UNICA:
                return validateMultipleChoiceSingle(question, request, maxScore);
            case OPCION_MULTIPLE:
                return validateMultipleChoiceMulti(question, request, maxScore);
            case VERDADERO_FALSO:
                return validateTrueFalse(question, request, maxScore);
            case ABIERTA_CORTA:
                return validateOpenShort(question, request, maxScore);
            case ABIERTA_LARGA:
                return validateOpenLong(question, request, maxScore);
            case NUMERICA:
                return validateNumeric(question, request, maxScore);
            case ESCALA:
                return validateScale(question, request, maxScore);
            case ORDENAMIENTO:
                return validateOrdering(question, request, maxScore);
            case EMPAREJAMIENTO:
                return validateMatching(question, request, maxScore);
            default:
                return ValidationResult.incorrect(BigDecimal.ZERO);
        }
    }
    
    private ValidationResult validateMultipleChoiceSingle(Pregunta question, SolicitudValidacionRespuesta request, BigDecimal maxScore) {
        if (request.idOpcionSeleccionada() == null) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof MultipleChoiceSingleQuestion) {
            MultipleChoiceSingleQuestion mcq = (MultipleChoiceSingleQuestion) question;
            boolean isCorrect = mcq.getCorrectOptionId().equals(request.idOpcionSeleccionada());
            return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateMultipleChoiceMulti(Pregunta question, SolicitudValidacionRespuesta request, BigDecimal maxScore) {
        if (request.idsOpcionesSeleccionadas() == null || request.idsOpcionesSeleccionadas().isEmpty()) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof MultipleChoiceMultiQuestion) {
            MultipleChoiceMultiQuestion mcq = (MultipleChoiceMultiQuestion) question;
            // Validación simple: si alguna opción seleccionada es correcta, damos puntos parciales
            boolean hasCorrectOption = mcq.getCorrectOptionIds().stream()
                .anyMatch(correctId -> request.idsOpcionesSeleccionadas().contains(correctId));
            
            if (hasCorrectOption) {
                return ValidationResult.correct(maxScore);
            }
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateTrueFalse(Pregunta question, SolicitudValidacionRespuesta request, BigDecimal maxScore) {
        if (request.respuestaBooleana() == null) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof TrueFalseQuestion) {
            TrueFalseQuestion tfq = (TrueFalseQuestion) question;
            boolean isCorrect = tfq.isCorrectAnswer() == request.respuestaBooleana();
            return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateOpenShort(Pregunta question, SolicitudValidacionRespuesta request, BigDecimal maxScore) {
        if (request.respuestaTexto() == null || request.respuestaTexto().trim().isEmpty()) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof OpenShortQuestion) {
            OpenShortQuestion osq = (OpenShortQuestion) question;
            List<String> correctAnswers = osq.getAcceptedAnswers();
            final String userAnswer = request.respuestaTexto().trim();
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
    
    private ValidationResult validateOpenLong(Pregunta question, SolicitudValidacionRespuesta request, BigDecimal maxScore) {
        if (request.respuestaTexto() == null || request.respuestaTexto().trim().isEmpty()) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        // Para ensayos, siempre se califica como correcto pero requiere revisión manual
        return ValidationResult.correct(maxScore);
    }
    
    private ValidationResult validateNumeric(Pregunta question, SolicitudValidacionRespuesta request, BigDecimal maxScore) {
        if (request.respuestaNumerica() == null) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof NumericQuestion) {
            NumericQuestion nq = (NumericQuestion) question;
            BigDecimal correctAnswer = nq.getCorrectValue();
            BigDecimal tolerance = nq.getTolerance() != null ? nq.getTolerance() : BigDecimal.ZERO;
            
            BigDecimal difference = request.respuestaNumerica().subtract(correctAnswer).abs();
            boolean isCorrect = difference.compareTo(tolerance) <= 0;
            
            return isCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateScale(Pregunta question, SolicitudValidacionRespuesta request, BigDecimal maxScore) {
        if (request.valorEscala() == null) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        // Para escalas, cualquier respuesta válida es aceptada
        return ValidationResult.correct(maxScore);
    }
    
    private ValidationResult validateOrdering(Pregunta question, SolicitudValidacionRespuesta request, BigDecimal maxScore) {
        if (request.idsItemsOrdenados() == null || request.idsItemsOrdenados().isEmpty()) {
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
            List<Long> userOrder = request.idsItemsOrdenados();
            
            if (correctOrder.equals(userOrder)) {
                return ValidationResult.correct(maxScore);
            }
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
    
    private ValidationResult validateMatching(Pregunta question, SolicitudValidacionRespuesta request, BigDecimal maxScore) {
        if (request.paresEmparejados() == null || request.paresEmparejados().isEmpty()) {
            return ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        if (question instanceof MatchingQuestion) {
            MatchingQuestion mq = (MatchingQuestion) question;
            // Para matching, los pares correctos están en la lista pairs
            List<MatchingPair> correctPairs = mq.getPairs();
            
            // Map<Long,Long>: comparar como identificadores numéricos frente a los textos almacenados en el par
            boolean allCorrect = request.paresEmparejados().entrySet().stream()
                .allMatch(entry -> correctPairs.stream()
                    .anyMatch(pair -> pair.getLeftItem().equals(String.valueOf(entry.getKey()))
                        && pair.getRightItem().equals(String.valueOf(entry.getValue()))));
            
            return allCorrect ? ValidationResult.correct(maxScore) : ValidationResult.incorrect(BigDecimal.ZERO);
        }
        
        return ValidationResult.incorrect(BigDecimal.ZERO);
    }
}

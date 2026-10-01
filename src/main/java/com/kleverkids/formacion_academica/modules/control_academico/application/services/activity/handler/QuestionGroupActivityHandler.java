package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.ResultadoAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.ContentKind;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/** Grupo de preguntas: Activity (type=QUESTION_GROUP) 1:N Pregunta existente. */
@Component("QUESTION_GROUP")
public class QuestionGroupActivityHandler implements ActivityHandler {

    @Override
    public void validarPublicacion(List<ActivityContentItem> contenido) {
        boolean tieneAlMenosUnaPregunta = contenido.stream()
                .anyMatch(i -> i.getContentKind() == ContentKind.QUESTION);
        if (!tieneAlMenosUnaPregunta) {
            throw new IllegalStateException(
                    "Un grupo de preguntas debe tener al menos una pregunta asociada para poder publicarse");
        }
    }

    @Override
    public boolean puedeCompletarse(List<ActivityContentItem> contenido, List<ActivityAttemptAnswer> respuestas) {
        Set<Long> preguntasDelGrupo = contenido.stream()
                .filter(i -> i.getContentKind() == ContentKind.QUESTION)
                .map(ActivityContentItem::getId)
                .collect(Collectors.toSet());
        Set<Long> respondidas = respuestas.stream()
                .map(ActivityAttemptAnswer::getContentItemId)
                .collect(Collectors.toSet());
        return respondidas.containsAll(preguntasDelGrupo);
    }

    @Override
    public ResultadoAttemptDto calcularResultado(Activity activity, List<ActivityContentItem> contenido, List<ActivityAttemptAnswer> respuestas) {
        BigDecimal score = respuestas.stream()
                .map(ActivityAttemptAnswer::getPuntajeObtenido)
                .filter(java.util.Objects::nonNull)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        boolean aprobado = activity.getNotaMinima() == null || score.compareTo(activity.getNotaMinima()) >= 0;
        return ResultadoAttemptDto.builder().score(score).aprobado(aprobado).build();
    }
}

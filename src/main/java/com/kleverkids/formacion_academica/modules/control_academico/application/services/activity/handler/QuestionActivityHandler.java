package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.ResultadoAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.ContentKind;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/** Actividad de una sola pregunta: Activity (type=QUESTION) 1:1 Pregunta existente. */
@Component("QUESTION")
public class QuestionActivityHandler implements ActivityHandler {

    @Override
    public void validarPublicacion(List<ActivityContentItem> contenido) {
        long preguntas = contenido.stream().filter(i -> i.getContentKind() == ContentKind.QUESTION).count();
        if (preguntas != 1) {
            throw new IllegalStateException(
                    "Una actividad de tipo Pregunta debe tener exactamente una pregunta asociada para poder publicarse");
        }
    }

    @Override
    public boolean puedeCompletarse(List<ActivityContentItem> contenido, List<ActivityAttemptAnswer> respuestas) {
        return !respuestas.isEmpty();
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

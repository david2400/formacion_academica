package com.kleverkids.formacion_academica.modules.control_academico.application.services.activity.handler;

import com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_attempt.ResultadoAttemptDto;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.Activity;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.ActivityContentItem;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.ContentKind;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity_attempt.ActivityAttemptAnswer;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/**
 * Actividad de contenido de lectura/texto enriquecido. No tiene preguntas que
 * calificar: completarla es un acto de "consumir contenido", no de responder
 * — por eso siempre puede completarse y siempre se considera aprobada
 * (no existe el concepto de reprobar una lectura).
 */
@Component("CONTENT")
public class ContentActivityHandler implements ActivityHandler {

    @Override
    public void validarPublicacion(List<ActivityContentItem> contenido) {
        boolean tieneTexto = contenido.stream().anyMatch(i -> i.getContentKind() == ContentKind.TEXT);
        if (!tieneTexto) {
            throw new IllegalStateException(
                    "Una actividad de contenido debe tener al menos un bloque de texto para poder publicarse");
        }
    }

    @Override
    public boolean puedeCompletarse(List<ActivityContentItem> contenido, List<ActivityAttemptAnswer> respuestas) {
        return true;
    }

    @Override
    public ResultadoAttemptDto calcularResultado(Activity activity, List<ActivityContentItem> contenido, List<ActivityAttemptAnswer> respuestas) {
        BigDecimal score = activity.getPuntos() != null ? activity.getPuntos() : BigDecimal.ZERO;
        return ResultadoAttemptDto.builder().score(score).aprobado(true).build();
    }
}

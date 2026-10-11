package com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackParticipation;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackResponse;

import java.util.Collection;
import java.util.Set;

public interface FeedbackResponseRepositoryPort {

    /**
     * Guarda respuesta + respuestas por pregunta + participación en la misma
     * transacción. Si la participación ya existe (doble envío concurrente)
     * lanza IllegalStateException: la PK de participación es la barrera final.
     */
    void registrar(FeedbackResponse respuesta, FeedbackParticipation participacion);

    long contarParticipaciones(Long assignmentId, Long claseId, Long estudianteId);

    /**
     * De los pares (aplicación, clase) dados, cuáles ya respondió el
     * estudiante. Una sola consulta para toda la bandeja (evita N+1).
     */
    Set<ClaseAplicacionKey> respondidas(Long estudianteId, Collection<ClaseAplicacionKey> candidatas);

    record ClaseAplicacionKey(Long assignmentId, Long claseId) {
    }
}

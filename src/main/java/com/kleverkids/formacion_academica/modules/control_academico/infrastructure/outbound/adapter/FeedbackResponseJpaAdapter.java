package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.adapter;

import com.kleverkids.formacion_academica.modules.control_academico.application.output.feedback.FeedbackResponseRepositoryPort;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackAnswer;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackParticipation;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_response.FeedbackResponse;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackAnswerEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackParticipationEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackParticipationId;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback.FeedbackResponseEntity;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackAnswerJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackParticipationJpaRepository;
import com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.repository.FeedbackResponseJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Persistencia de respuestas. Mapeo manual a propósito: deja a la vista que
 * la respuesta y la participación no comparten ningún dato que permita
 * unirlas (ver FeedbackParticipationEntity).
 */
@Component
@RequiredArgsConstructor
public class FeedbackResponseJpaAdapter implements FeedbackResponseRepositoryPort {

    private static final String YA_RESPONDIO = "Ya respondiste este feedback. ¡Gracias!";

    private final FeedbackResponseJpaRepository responseRepository;
    private final FeedbackAnswerJpaRepository answerRepository;
    private final FeedbackParticipationJpaRepository participationRepository;
    private final EntityManager entityManager;

    @Override
    public void registrar(FeedbackResponse respuesta, FeedbackParticipation participacion) {
        FeedbackParticipationId id = new FeedbackParticipationId(participacion.getAssignmentId(),
                participacion.getClaseId(), participacion.getEstudianteId(), participacion.getNumero());
        if (participationRepository.existsById(id)) {
            throw new IllegalStateException(YA_RESPONDIO);
        }
        try {
            // persist (no save/merge): con clave asignada, merge convertiría un
            // doble envío concurrente en UPDATE silencioso; persist + flush hace
            // que la PK lo rechace.
            entityManager.persist(FeedbackParticipationEntity.builder().id(id).fecha(participacion.getFecha()).build());
            entityManager.flush();
        } catch (PersistenceException | DataIntegrityViolationException e) {
            throw new IllegalStateException(YA_RESPONDIO);
        }

        FeedbackResponseEntity cabecera = responseRepository.save(FeedbackResponseEntity.builder()
                .assignmentId(respuesta.getAssignmentId())
                .formId(respuesta.getFormId())
                .claseId(respuesta.getClaseId())
                .estudianteId(respuesta.getEstudianteId())
                .enviadoEn(respuesta.getEnviadoEn())
                .build());

        List<FeedbackAnswerEntity> filas = respuesta.getRespuestas().stream()
                .map(a -> toEntity(a, cabecera.getId()))
                .toList();
        answerRepository.saveAll(filas);
    }

    @Override
    public long contarParticipaciones(Long assignmentId, Long claseId, Long estudianteId) {
        return participationRepository.countById_AssignmentIdAndId_ClaseIdAndId_EstudianteId(assignmentId, claseId, estudianteId);
    }

    @Override
    public Set<ClaseAplicacionKey> respondidas(Long estudianteId, Collection<ClaseAplicacionKey> candidatas) {
        if (candidatas.isEmpty()) {
            return Set.of();
        }
        Set<Long> aplicaciones = candidatas.stream().map(ClaseAplicacionKey::assignmentId).collect(Collectors.toSet());
        Set<ClaseAplicacionKey> todas = new HashSet<>();
        for (Object[] fila : participationRepository.respondidas(estudianteId, aplicaciones)) {
            todas.add(new ClaseAplicacionKey((Long) fila[0], (Long) fila[1]));
        }
        todas.retainAll(new HashSet<>(candidatas));
        return todas;
    }

    private static FeedbackAnswerEntity toEntity(FeedbackAnswer a, Long responseId) {
        return FeedbackAnswerEntity.builder()
                .responseId(responseId)
                .questionId(a.getQuestionId())
                .optionId(a.getOptionId())
                .valorNumerico(a.getValorNumerico())
                .valorNormalizado(a.getValorNormalizado())
                .texto(a.getTexto())
                .build();
    }
}

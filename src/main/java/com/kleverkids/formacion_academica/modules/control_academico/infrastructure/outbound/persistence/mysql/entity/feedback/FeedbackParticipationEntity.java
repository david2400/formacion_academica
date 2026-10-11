package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

/**
 * Quién respondió qué (aplicación, clase). Sin id autoincremental, sin hora y
 * sin AuditInfo, a propósito: cualquiera de los tres permitiría ordenar
 * participaciones y respuestas por inserción y emparejarlas, rompiendo el
 * anonimato. Ver FeedbackParticipation en el dominio.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback_participations", indexes = {
        // Hibernate ordena las columnas de la PK embebida alfabéticamente
        // (numero queda primero), así que la PK no sirve para buscar por
        // (aplicación, clase[, estudiante]): este índice cubre esas consultas
        // y el conteo de participación por clase.
        @Index(name = "idx_feedback_participations_lookup", columnList = "assignment_id, clase_id, estudiante_id"),
        @Index(name = "idx_feedback_participations_estudiante", columnList = "estudiante_id")
})
public class FeedbackParticipationEntity {

    @EmbeddedId
    private FeedbackParticipationId id;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;
}

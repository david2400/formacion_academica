package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Clave natural de una participación. Es la restricción de base de datos que
 * impide responder dos veces: numero = 1 salvo formularios con respuestas
 * múltiples (mismo truco que attempt_number en ActivityAttempt).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class FeedbackParticipationId implements Serializable {

    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @Column(name = "clase_id", nullable = false)
    private Long claseId;

    @Column(name = "estudiante_id", nullable = false)
    private Long estudianteId;

    @Column(name = "numero", nullable = false)
    private Integer numero;
}

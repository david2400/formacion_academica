package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Cabecera de una respuesta enviada.
 *
 * <p><b>Desviación consciente: NO extiende AuditInfo.</b> AuditInfo registra
 * usr_crea/created_at; hoy usr_crea es 1 fijo, pero el día que se conecte el
 * usuario real quedaría el id del estudiante en cada respuesta anónima. Esta
 * tabla tampoco necesita soft delete (las respuestas no se editan ni borran).
 * El instante de envío lo fija el servicio (truncado a la hora si es anónima).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "feedback_responses", indexes = {
        @Index(name = "idx_feedback_responses_assignment_clase", columnList = "assignment_id, clase_id"),
        @Index(name = "idx_feedback_responses_clase", columnList = "clase_id"),
        @Index(name = "idx_feedback_responses_form_fecha", columnList = "form_id, enviado_en"),
        @Index(name = "idx_feedback_responses_estudiante", columnList = "estudiante_id")
})
public class FeedbackResponseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @Column(name = "form_id", nullable = false)
    private Long formId;

    @Column(name = "clase_id", nullable = false)
    private Long claseId;

    /** NULL si el formulario es anónimo. */
    @Column(name = "estudiante_id")
    private Long estudianteId;

    @Column(name = "enviado_en", nullable = false)
    private LocalDateTime enviadoEn;
}

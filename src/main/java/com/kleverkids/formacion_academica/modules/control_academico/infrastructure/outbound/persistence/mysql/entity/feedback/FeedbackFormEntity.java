package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.feedback;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Formulario de feedback. Referencias como Long suelto (patrón empírico del
 * proyecto); secciones/preguntas se cargan con consultas propias por form_id
 * (no EAGER anidado) para evitar N+1 y productos cartesianos.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "feedback_forms", indexes = {
        @Index(name = "idx_feedback_forms_estado", columnList = "estado_id"),
        @Index(name = "idx_feedback_forms_origen", columnList = "origen_form_id")
})
public class FeedbackFormEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "titulo", nullable = false, length = 200)
    private String titulo;

    @Column(name = "descripcion", length = 1000)
    private String descripcion;

    @Column(name = "anonimo", nullable = false)
    private boolean anonimo;

    @Column(name = "permite_multiples_respuestas", nullable = false)
    private boolean permiteMultiplesRespuestas;

    @Column(name = "version", nullable = false)
    private Integer version;

    @Column(name = "origen_form_id")
    private Long origenFormId;

    /** Réplica del estado del motor (FEEDBACK_FORM_LIFECYCLE). Sin FK: vive en el esquema security. */
    @Column(name = "estado_id")
    private Long estadoId;
}

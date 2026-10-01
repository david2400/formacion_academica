package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_assignment;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_assignment.valueobject.EstadoLearningAssignment;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.CheckConstraint;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;

/**
 * Asignación de una LearningSequence a un grupo o a un estudiante puntual.
 * Exactamente uno de grupoId/estudianteId está informado, forzado también a
 * nivel de base de datos (igual que el CHECK ya usado en
 * ActivityDependencyRuleEntity — ver nota ahí sobre el atributo correcto de
 * la anotación, "constraint" singular, no "constraints").
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "learning_assignment",
        check = @CheckConstraint(constraint = "(grupo_id IS NULL) <> (estudiante_id IS NULL)"))
public class LearningAssignmentEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK operacional directa (no sequence_version_id) — ver nota de corrección en LearningAssignment (dominio). */
    @Column(name = "sequence_id", nullable = false)
    private Long sequenceId;

    /** FK a LearningSequenceVersion.id vigente al asignar; solo trazabilidad. */
    @Column(name = "sequence_version_id")
    private Long sequenceVersionId;

    @Column(name = "grupo_id")
    private Long grupoId;

    @Column(name = "estudiante_id")
    private Long estudianteId;

    @Column(name = "assigned_by", nullable = false)
    private Long assignedBy;

    @Column(name = "assigned_at", nullable = false)
    private Instant assignedAt;

    @Column(name = "available_from")
    private Instant availableFrom;

    @Column(name = "due_at")
    private Instant dueAt;

    @Column(name = "allow_late_submission", nullable = false)
    private boolean allowLateSubmission;

    @Column(name = "lock_after_due", nullable = false)
    private boolean lockAfterDue;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false, length = 20)
    private EstadoLearningAssignment estado;
}

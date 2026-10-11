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

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Aplicación de un formulario. Exactamente uno de clase_id / grupo_id (lo
 * valida el servicio; MySQL 8 soporta CHECK pero el proyecto no lo usa salvo
 * en ActivityDependencyRuleEntity, y Hibernate no lo agrega a una tabla
 * existente con ddl-auto=update).
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "feedback_assignments", indexes = {
        @Index(name = "idx_feedback_assignments_form", columnList = "form_id"),
        @Index(name = "idx_feedback_assignments_clase", columnList = "clase_id"),
        @Index(name = "idx_feedback_assignments_grupo", columnList = "grupo_id, desde, hasta"),
        @Index(name = "idx_feedback_assignments_estado", columnList = "estado_id")
})
public class FeedbackAssignmentEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "form_id", nullable = false)
    private Long formId;

    @Column(name = "nombre", nullable = false, length = 200)
    private String nombre;

    @Column(name = "clase_id")
    private Long claseId;

    @Column(name = "grupo_id")
    private Long grupoId;

    @Column(name = "desde")
    private LocalDate desde;

    @Column(name = "hasta")
    private LocalDate hasta;

    @Column(name = "apertura_offset_minutos", nullable = false)
    private Integer aperturaOffsetMinutos;

    @Column(name = "vigencia_horas", nullable = false)
    private Integer vigenciaHoras;

    @Column(name = "abre_en")
    private LocalDateTime abreEn;

    @Column(name = "cierra_en")
    private LocalDateTime cierraEn;

    @Column(name = "solo_asistentes", nullable = false)
    private boolean soloAsistentes;

    @Column(name = "umbral_anonimato", nullable = false)
    private Integer umbralAnonimato;

    /** Réplica del estado del motor (FEEDBACK_APLICACION_LIFECYCLE). */
    @Column(name = "estado_id")
    private Long estadoId;
}

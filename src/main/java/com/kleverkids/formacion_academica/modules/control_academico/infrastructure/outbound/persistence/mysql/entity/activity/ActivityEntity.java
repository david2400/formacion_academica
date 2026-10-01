package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.EstadoActivity;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
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

import java.math.BigDecimal;

/**
 * Actividad de aprendizaje (lo que antes era ActividadEntity, migrado — ver
 * arquitectura aprobada, decisión §2.1). Sin cursoId/moduloId (conceptos
 * huérfanos de la versión anterior); la pertenencia a una secuencia se
 * resolverá en Etapa 2 vía LearningSequenceItem.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "activity")
public class ActivityEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** FK a activity_type_definition.id. Sin relación JPA (ver ExamenPreguntaEntity: mismo patrón del módulo). */
    @Column(name = "activity_type_id", nullable = false)
    private Long activityTypeId;

    @Column(name = "titulo", nullable = false)
    private String titulo;

    @Column(name = "descripcion", length = 2000)
    private String descripcion;

    @Column(name = "instrucciones", length = 2000)
    private String instrucciones;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false, length = 20)
    private EstadoActivity estado;

    @Column(name = "estimated_duration_minutes")
    private Integer duracionEstimadaMinutos;

    @Column(name = "points", precision = 6, scale = 2)
    private BigDecimal puntos;

    @Column(name = "passing_score", precision = 6, scale = 2)
    private BigDecimal notaMinima;

    @Column(name = "is_required", nullable = false)
    private boolean esObligatoria;

    @Column(name = "allow_retry", nullable = false)
    private boolean permiteReintentos;

    /** NULL = intentos ilimitados. */
    @Column(name = "max_attempts")
    private Integer maxIntentos;

    /** FK a activity_version.id de la última publicación. NULL mientras es solo DRAFT. */
    @Column(name = "current_version_id")
    private Long versionActualId;
}

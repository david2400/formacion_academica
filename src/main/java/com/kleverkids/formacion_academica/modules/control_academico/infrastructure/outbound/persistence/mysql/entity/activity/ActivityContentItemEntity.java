package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.ContentKind;
import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.util.Map;

/**
 * Una pieza de contenido de una Activity. Para QUESTION, FK directo (sin
 * snapshot) a questions.id (ver decisión §2.2 de la arquitectura aprobada).
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "activity_content_item",
        uniqueConstraints = @UniqueConstraint(columnNames = {"activity_id", "orden"}))
public class ActivityContentItemEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    @Enumerated(EnumType.STRING)
    @Column(name = "content_kind", nullable = false, length = 20)
    private ContentKind contentKind;

    /** FK directo a questions.id cuando contentKind=QUESTION. Mismo módulo (ver PreguntaEntity). */
    @Column(name = "pregunta_id")
    private Long preguntaId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "texto", columnDefinition = "json")
    private Map<String, Object> texto;

    @Column(name = "puntos", precision = 6, scale = 2)
    private BigDecimal puntos;
}

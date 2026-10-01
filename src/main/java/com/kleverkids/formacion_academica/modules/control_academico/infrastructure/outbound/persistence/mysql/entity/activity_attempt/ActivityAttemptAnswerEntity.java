package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity_attempt;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

/**
 * No extiende AuditInfo: una respuesta de intento es un hecho simple
 * (registrar/reemplazar antes de completar), sin soft-delete ni autor de
 * modificación — mismo criterio ya aplicado a ActivityDependencyRuleEntity.
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "activity_attempt_answer",
        uniqueConstraints = @UniqueConstraint(columnNames = {"attempt_id", "content_item_id"}))
public class ActivityAttemptAnswerEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "attempt_id", nullable = false)
    private Long attemptId;

    @Column(name = "content_item_id", nullable = false)
    private Long contentItemId;

    @Column(name = "pregunta_id")
    private Long preguntaId;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "respuesta", columnDefinition = "json")
    private Map<String, Object> respuestaJson;

    @Column(name = "es_correcta")
    private Boolean esCorrecta;

    @Column(name = "puntaje_obtenido", precision = 6, scale = 2)
    private BigDecimal puntajeObtenido;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "pregunta_snapshot", columnDefinition = "json")
    private Map<String, Object> preguntaSnapshot;

    @Column(name = "registrada_en", nullable = false)
    private Instant registradaEn;
}

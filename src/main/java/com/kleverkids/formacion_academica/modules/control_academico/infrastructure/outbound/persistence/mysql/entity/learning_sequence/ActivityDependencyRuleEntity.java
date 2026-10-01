package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoReglaDependencia;
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
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;

/**
 * No extiende AuditInfo: una regla de dependencia es un hecho simple
 * (crear/borrar), sin soft-delete ni autor de modificación — igual de
 * razonable que `ExamenPreguntaEntity`, pero esa sí hereda AuditInfo por
 * convención del proyecto, así que aquí se mantiene createdAt propio para no
 * introducir una tercera convención sin necesidad real.
 */
@Data
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "activity_dependency_rule",
        check = @CheckConstraint(constraint = "item_id <> depends_on_item_id"))
public class ActivityDependencyRuleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "item_id", nullable = false)
    private Long itemId;

    @Column(name = "depends_on_item_id", nullable = false)
    private Long dependsOnItemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "rule_type", nullable = false, length = 20)
    private TipoReglaDependencia ruleType;

    @Column(name = "rule_valor_numerico", precision = 10, scale = 2)
    private BigDecimal ruleValorNumerico;

    @Column(name = "rule_valor_fecha")
    private Instant ruleValorFecha;

    @CreationTimestamp
    @Column(name = "created_at", updatable = false)
    private Instant createdAt;
}

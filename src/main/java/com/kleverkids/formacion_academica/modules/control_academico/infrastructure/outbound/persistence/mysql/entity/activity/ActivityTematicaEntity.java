package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.activity;

import com.kleverkids.formacion_academica.shared.common.domain.entity.AuditInfo;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

/**
 * Tabla puente actividad&lt;-&gt;temática, mismo patrón que
 * {@code ExamenTematicaEntity}: una actividad puede tener varias temáticas
 * (una fila por asignación), reutilizando el catálogo compartido
 * {@code Tematica} (el mismo que usan preguntas y exámenes) en vez de
 * inventar un concepto de "tema de actividad" distinto.
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "activities_tematicas")
public class ActivityTematicaEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "activity_id", nullable = false)
    private Long activityId;

    @Column(name = "tematica_id", nullable = false)
    private Long tematicaId;

}

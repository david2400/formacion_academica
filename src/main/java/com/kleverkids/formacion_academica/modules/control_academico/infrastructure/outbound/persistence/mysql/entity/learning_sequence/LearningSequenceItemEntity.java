package com.kleverkids.formacion_academica.modules.control_academico.infrastructure.outbound.persistence.mysql.entity.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
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

import java.time.Instant;

/**
 * Una fila de una secuencia (Activity o Grupo). {@code parentItemId} NULL =
 * nivel raíz.
 *
 * <p>Nota deliberada sobre integridad: el documento de arquitectura original
 * proponía un {@code UNIQUE(sequence_id, parent_item_id, orden)}, pero en
 * MySQL una columna NULL dentro de un índice único no se compara como igual
 * a otro NULL — dos items raíz (parentItemId=NULL) con el mismo
 * {@code orden} NO violarían ese constraint. Por eso aquí no se declara: el
 * orden sin duplicados se garantiza en la capa de aplicación (orden
 * calculado al agregar, reordenar valida explícitamente), igual que ya pasa
 * hoy con otras invariantes de este proyecto (ver nota en IntentoExamen).
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@SuperBuilder
@NoArgsConstructor
@Table(name = "learning_sequence_item")
public class LearningSequenceItemEntity extends AuditInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sequence_id", nullable = false)
    private Long sequenceId;

    @Column(name = "parent_item_id")
    private Long parentItemId;

    @Enumerated(EnumType.STRING)
    @Column(name = "item_type", nullable = false, length = 10)
    private TipoItemSecuencia itemType;

    @Column(name = "activity_id")
    private Long activityId;

    @Column(name = "titulo_grupo")
    private String tituloGrupo;

    @Column(name = "orden", nullable = false)
    private Integer orden;

    @Column(name = "es_obligatoria", nullable = false)
    private boolean esObligatoria;

    @Column(name = "available_from")
    private Instant disponibleDesde;

    @Column(name = "due_at")
    private Instant fechaLimite;
}

package com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.TipoItemSecuencia;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Una fila de la secuencia: una Activity o un Grupo. {@code parentItemId} NULL
 * significa nivel raíz; un Grupo no puede estar anidado dentro de otro Grupo
 * (un solo nivel — ver arquitectura aprobada §2.3).
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LearningSequenceItem {

    private Long id;
    private Long sequenceId;

    /** NULL = nivel raíz. */
    private Long parentItemId;

    private TipoItemSecuencia itemType;

    /** Requerido si itemType=ACTIVITY. */
    private Long activityId;

    /** Requerido si itemType=GROUP. */
    private String tituloGrupo;

    private Integer orden;
    private boolean esObligatoria;

    private LocalDateTime disponibleDesde;
    private LocalDateTime fechaLimite;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

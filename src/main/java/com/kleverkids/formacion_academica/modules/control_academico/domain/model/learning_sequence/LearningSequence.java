package com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence;

import com.kleverkids.formacion_academica.modules.control_academico.domain.events.learning_sequence.LearningSequencePublishedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.learning_sequence.valueobject.EstadoLearningSequence;
import com.kleverkids.formacion_academica.shared.common.domain.AggregateRoot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Agregado raíz de una secuencia de aprendizaje: un conjunto ordenado de
 * actividades (y grupos de actividades) con dependencias de desbloqueo entre
 * ellas. Segunda clase de dominio del proyecto (después de {@code Activity})
 * que adopta {@link AggregateRoot} de verdad, por la misma razón: publicar
 * una secuencia es un evento de dominio real que otros componentes podrían
 * querer escuchar (ej. notificar a los estudiantes asignados, en Etapa 3).
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class LearningSequence extends AggregateRoot<Long> {

    private Long id;
    private String titulo;
    private String descripcion;
    private EstadoLearningSequence estado;

    /** FK a LearningSequenceVersion.id de la última publicación. NULL mientras es solo DRAFT. */
    private Long versionActualId;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @Override
    public Long getId() {
        return id;
    }

    /** Ver {@code Activity.registrarEventoPublicacion()}: se invoca después de persistir. */
    public void registrarEventoPublicacion() {
        registerEvent(new LearningSequencePublishedEvent(this.id, this.versionActualId));
    }
}

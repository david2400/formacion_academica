package com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity;

import com.kleverkids.formacion_academica.modules.control_academico.domain.events.activity.ActivityPublishedEvent;
import com.kleverkids.formacion_academica.modules.control_academico.domain.model.activity.valueobject.EstadoActivity;
import com.kleverkids.formacion_academica.shared.common.domain.AggregateRoot;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Agregado raíz de una actividad de aprendizaje (lo que antes era
 * {@code Actividad}, migrado — ver arquitectura aprobada, decisión §2.1).
 * Es la primera entidad de dominio del proyecto que adopta {@link AggregateRoot}
 * de verdad, acumulando eventos en vez de publicarlos sueltos desde el
 * Service (ver §2.6 de la arquitectura aprobada).
 */
@Data
@EqualsAndHashCode(callSuper = false)
@NoArgsConstructor
public class Activity extends AggregateRoot<Long> {

    private Long id;

    /** FK a ActivityTypeDefinition.id (registro extensible de tipos). */
    private Long activityTypeId;

    private String titulo;
    private String descripcion;
    private String instrucciones;
    private EstadoActivity estado;
    private Integer duracionEstimadaMinutos;
    private BigDecimal puntos;
    private BigDecimal notaMinima;
    private boolean esObligatoria;
    private boolean permiteReintentos;

    /** NULL = intentos ilimitados. */
    private Integer maxIntentos;

    /** FK a ActivityVersion.id de la última publicación (NULL mientras es solo DRAFT). */
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

    /**
     * Registra el evento de dominio de publicación. Se invoca después de que
     * el adapter ya persistió el nuevo estado PUBLISHED + la ActivityVersion
     * (de ahí que no mute campos aquí: este objeto ya refleja el estado
     * posterior a guardar). El Service es responsable de recorrer
     * {@link #getDomainEvents()}, publicarlos y limpiarlos.
     */
    public void registrarEventoPublicacion() {
        registerEvent(new ActivityPublishedEvent(this.id, this.activityTypeId, this.versionActualId));
    }
}

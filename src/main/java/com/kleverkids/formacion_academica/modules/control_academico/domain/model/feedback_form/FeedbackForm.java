package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Plantilla reutilizable de feedback (secciones + preguntas).
 *
 * <p>Ciclo de vida en el motor de estados ({@code FEEDBACK_FORM_LIFECYCLE}):
 * BORRADOR → PUBLICADO → ARCHIVADO. Una vez PUBLICADO es inmutable porque las
 * respuestas apuntan a sus preguntas: modificarlas rompería la analítica
 * histórica. "Editar" un publicado = duplicarlo como nueva versión
 * ({@link #origenFormId} apunta a la versión 1 de la familia).
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackForm {

    private Long id;
    private String titulo;
    private String descripcion;

    /** Si es anónimo, la respuesta nunca guarda estudiante_id (ver FeedbackResponse). */
    private boolean anonimo;

    /** Permite que un mismo estudiante responda varias veces la misma clase. */
    private boolean permiteMultiplesRespuestas;

    private Integer version;

    /** Primera versión de la familia; NULL en la propia versión 1. */
    private Long origenFormId;

    /** Réplica del estado en el motor (FEEDBACK_FORM_LIFECYCLE); el motor manda. */
    private Long estadoId;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

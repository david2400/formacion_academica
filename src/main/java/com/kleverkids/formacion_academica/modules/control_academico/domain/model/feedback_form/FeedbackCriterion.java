package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Criterio estable de evaluación (Claridad, Dominio del tema, Metodología...).
 *
 * <p>Sin él, la tendencia histórica se pierde cada vez que cambia el texto de
 * una pregunta o se publica una nueva versión del formulario: el criterio es
 * la clave que permanece. No se reutiliza {@code CriterioEvaluados} porque es
 * un criterio de calificación de exámenes (con recomendación base), no de
 * percepción.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackCriterion {

    private Long id;
    private String codigo;
    private String nombre;
    private String descripcion;
    private boolean activo;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

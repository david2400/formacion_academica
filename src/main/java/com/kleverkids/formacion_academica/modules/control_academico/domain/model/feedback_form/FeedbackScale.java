package com.kleverkids.formacion_academica.modules.control_academico.domain.model.feedback_form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Escala reutilizable (ej. "Likert 1-5 calidad"). Existe para no repetir las
 * mismas etiquetas en cada pregunta y para que todas las preguntas que la usan
 * sean comparables entre sí.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FeedbackScale {

    private Long id;
    private String nombre;
    private String descripcion;
    private boolean activa;

    private boolean eliminado;
    private Integer usrCrea;
    private Integer usrMod;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}

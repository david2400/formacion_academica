package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.activity_tematica;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Asigna una temática existente (catálogo compartido con preguntas y
 * exámenes, ver {@code Tematica}) a una actividad. Una actividad puede tener
 * varias temáticas (una fila por asignación, igual que
 * {@code CrearExamenTematicaDto}) — a diferencia de una pregunta individual,
 * que tiene una sola temática (ver {@code PreguntaBanco#tematicaId}).
 */
@Data
@NoArgsConstructor
public class CrearActivityTematicaDto {

    @NotNull(message = "El identificador de la actividad es obligatorio")
    private Long activityId;

    @NotNull(message = "El identificador de la temática es obligatorio")
    private Long tematicaId;

}

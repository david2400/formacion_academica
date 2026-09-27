package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Actualiza únicamente el nombre y/o la descripción de un examen ya creado,
 * sin tocar preguntas, criterios ni temáticas asignadas. Es una actualización
 * parcial: los campos en null se ignoran y el resto conserva su valor actual.
 * Usa el camino legado (Examen/ExamenEntity), el único que persiste realmente
 * -ver ExamenJpaAdapter.actualizarInfoBasica-, a diferencia de PUT
 * /examenes/{id}, que depende del agregado Exam todavía no conectado a la
 * base de datos.
 */
@Data
@NoArgsConstructor
public class ActualizarInfoBasicaExamenDto {

    @Size(min = 1, message = "El nombre no puede estar vacío")
    private String nombre;

    private String descripcion;
}

package com.kleverkids.formacion_academica.modules.admisiones.domain.dto.inscripcion;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cambia el estado de una inscripción.
 *
 * <p>Ya no hereda de {@code CrearInscripcionDto}: un cambio de estado no debería
 * exigir reenviar estudiante, periodo y fecha de solicitud.
 *
 * <p>{@code nuevoEstadoId} tiene que ser un estado de {@code INSCRIPCION_LIFECYCLE}
 * <b>y</b> ser alcanzable desde el estado actual: el motor traduce ese destino a la
 * acción que lleva hasta él y rechaza la operación si no hay ninguna. Los ids
 * válidos salen de
 * {@code GET /api/access_control/state-machines/by-code/INSCRIPCION_LIFECYCLE}, y
 * los alcanzables ahora mismo de
 * {@code GET /api/access_control/entities/INSCRIPCION/{id}/state-machine}.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstadoInscripcionDto {

    @NotNull(message = "La inscripción es obligatoria")
    private Long inscripcionId;

    @NotNull(message = "El nuevo estado es obligatorio")
    private Long nuevoEstadoId;

    /** Opcional: multi-empresa. Null o 0 usa la parametrización global. */
    private Long idEmpresa;

    /**
     * Motivo del cambio. Opcional aquí, pero el motor de estados lo exige en las
     * transiciones configuradas con {@code requires_reason} —rechazar una
     * inscripción, por ejemplo— y las rechaza si llega vacío.
     */
    private String motivo;
}

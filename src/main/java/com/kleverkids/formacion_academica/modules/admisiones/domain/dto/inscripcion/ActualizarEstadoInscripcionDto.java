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
 * <p>{@code nuevoEstadoId} debe estar habilitado para el contexto
 * {@code formacion_academica.admisiones.inscripcion} en el catálogo central; si no,
 * la operación se rechaza. Los ids válidos se obtienen de
 * {@code /api/kleverkids/catalogo-estados/contextos/{codigo}/estados}.
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
}

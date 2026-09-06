package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Cambia el estado de una asignación.
 *
 * <p>{@code nuevoEstadoId} debe ser un estado parametrizado para el contexto
 * {@code estudiante_grupo}; en caso contrario la operación se rechaza. El cliente
 * obtiene los ids válidos de {@code /estados/contextos/estudiante_grupo}, nunca los
 * fija en código.
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoEstudianteGrupoDto {

    @NotNull(message = "La asignación es obligatoria")
    private Long asignacionId;

    @NotNull(message = "El nuevo estado es obligatorio")
    private Long nuevoEstadoId;

    /** Opcional: multi-empresa. Null o 0 usa la parametrización global. */
    private Long idEmpresa;

    /**
     * Motivo del cambio. Opcional aquí, pero el motor de estados lo exige en las
     * transiciones configuradas con {@code requires_reason} —retirar a un estudiante
     * del grupo, por ejemplo— y las rechaza si llega vacío.
     */
    private String motivo;

    public Long asignacionId() {
        return asignacionId;
    }

    public Long nuevoEstadoId() {
        return nuevoEstadoId;
    }
}

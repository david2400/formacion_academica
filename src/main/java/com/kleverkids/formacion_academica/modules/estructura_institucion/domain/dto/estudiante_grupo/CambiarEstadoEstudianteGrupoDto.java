package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CambiarEstadoEstudianteGrupoDto {

    @NotNull(message = "La asignación es obligatoria")
    private UUID asignacionId;

    @NotBlank(message = "El nuevo estado es obligatorio")
    private String nuevoEstado;

    public UUID asignacionId() {
        return asignacionId;
    }

    public String nuevoEstado() {
        return nuevoEstado;
    }
}

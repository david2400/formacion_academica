package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignarEstudianteGrupoDto {

    @NotNull(message = "El estudiante es obligatorio")
    private Long estudianteId;

    @NotNull(message = "El grupo es obligatorio")
    private Long grupoId;

    private LocalDate fechaAsignacion;

    /** Opcional: multi-empresa. Null o 0 usa la parametrización global. */
    private Long idEmpresa;

    public Long estudianteId() {
        return estudianteId;
    }

    public Long grupoId() {
        return grupoId;
    }

    public LocalDate fechaAsignacion() {
        return fechaAsignacion;
    }
}

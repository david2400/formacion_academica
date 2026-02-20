package com.kleverkids.formacion_academica.modules.estructura_institucion.domain.dto.estudiante_grupo;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignarEstudianteGrupoDto {

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

    @NotNull(message = "El grupo es obligatorio")
    private UUID grupoId;

    private LocalDate fechaAsignacion;

    public UUID estudianteId() {
        return estudianteId;
    }

    public UUID grupoId() {
        return grupoId;
    }

    public LocalDate fechaAsignacion() {
        return fechaAsignacion;
    }
}

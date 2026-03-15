package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CrearEstudianteAcudienteDto {

    @NotNull(message = "El estudiante es obligatorio")
    private Long estudianteId;

    @NotNull(message = "El acudiente es obligatorio")
    private Long acudienteId;

    @NotBlank(message = "El parentesco es obligatorio")
    private String parentesco;

    private boolean esPrincipal;

    public Long estudianteId() {
        return estudianteId;
    }

    public Long acudienteId() {
        return acudienteId;
    }

    public String parentesco() {
        return parentesco;
    }

    public boolean esPrincipal() {
        return esPrincipal;
    }
}

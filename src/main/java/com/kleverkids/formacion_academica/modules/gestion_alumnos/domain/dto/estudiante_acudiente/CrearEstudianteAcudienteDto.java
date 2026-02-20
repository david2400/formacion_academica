package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente;

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
public class CrearEstudianteAcudienteDto {

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

    @NotNull(message = "El acudiente es obligatorio")
    private UUID acudienteId;

    @NotBlank(message = "El parentesco es obligatorio")
    private String parentesco;

    private boolean esPrincipal;

    public UUID estudianteId() {
        return estudianteId;
    }

    public UUID acudienteId() {
        return acudienteId;
    }

    public String parentesco() {
        return parentesco;
    }

    public boolean esPrincipal() {
        return esPrincipal;
    }
}

package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente;

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
public class ActualizarEstudianteAcudienteDto {

    @NotNull(message = "El identificador de la relación es obligatorio")
    private UUID relacionId;

    private String parentesco;

    private Boolean esPrincipal;

    private String estado;

    public UUID relacionId() {
        return relacionId;
    }

    public String parentesco() {
        return parentesco;
    }

    public Boolean esPrincipal() {
        return esPrincipal;
    }

    public String estado() {
        return estado;
    }
}

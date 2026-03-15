package com.kleverkids.formacion_academica.modules.gestion_alumnos.domain.dto.estudiante_acudiente;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarEstudianteAcudienteDto {

    @NotNull(message = "El identificador de la relación es obligatorio")
    private Long relacionId;

    private Long estudianteId;

    private String parentesco;

    private Boolean esPrincipal;

    private String estado;

    public Long relacionId() {
        return relacionId;
    }

    public Long estudianteId() {
        return estudianteId;
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

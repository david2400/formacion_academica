package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.estudiante_examen;

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
public class RegistrarEstudianteExamenDto {

    @NotNull(message = "El examen es obligatorio")
    private UUID examenId;

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

}

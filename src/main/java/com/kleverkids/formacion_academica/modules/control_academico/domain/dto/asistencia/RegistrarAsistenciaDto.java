package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;

@Data
@NoArgsConstructor
public class RegistrarAsistenciaDto {

    @NotNull(message = "La clase es obligatoria")
    private Long claseId;

    @NotNull(message = "El estudiante es obligatorio")
    private Long estudianteId;

    private LocalDateTime fechaRegistro;

    private boolean presente;
}

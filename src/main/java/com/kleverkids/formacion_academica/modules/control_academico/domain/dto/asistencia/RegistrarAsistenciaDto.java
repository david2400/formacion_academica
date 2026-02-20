package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.asistencia;

import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
public class RegistrarAsistenciaDto {

    @NotNull(message = "La clase es obligatoria")
    private UUID claseId;

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

    private LocalDateTime fechaRegistro;

    private boolean presente;
}

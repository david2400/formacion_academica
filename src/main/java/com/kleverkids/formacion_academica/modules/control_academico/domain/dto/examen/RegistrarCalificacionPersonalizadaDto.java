package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
public class RegistrarCalificacionPersonalizadaDto {

    @NotNull(message = "El examen es obligatorio")
    private UUID examenId;

    @NotNull(message = "El estudiante es obligatorio")
    private UUID estudianteId;

    @NotBlank(message = "El criterio es obligatorio")
    private String criterio;

    @NotNull(message = "El puntaje es obligatorio")
    @DecimalMin(value = "0.0", inclusive = true, message = "El puntaje debe ser mayor o igual a cero")
    private BigDecimal puntajeOtorgado;

    public RegistrarCalificacionPersonalizadaDto(UUID examenId,
                                                 UUID estudianteId,
                                                 String criterio,
                                                 BigDecimal puntajeOtorgado) {
        this.examenId = Objects.requireNonNull(examenId, "El examen es obligatorio");
        this.estudianteId = Objects.requireNonNull(estudianteId, "El estudiante es obligatorio");
        this.criterio = Objects.requireNonNull(criterio, "El criterio es obligatorio");
        if (this.criterio.isBlank()) {
            throw new IllegalArgumentException("El criterio es obligatorio");
        }
        this.puntajeOtorgado = Objects.requireNonNull(puntajeOtorgado, "El puntaje es obligatorio");
        if (BigDecimal.ZERO.compareTo(this.puntajeOtorgado) > 0) {
            throw new IllegalArgumentException("El puntaje debe ser mayor o igual a cero");
        }
    }
}

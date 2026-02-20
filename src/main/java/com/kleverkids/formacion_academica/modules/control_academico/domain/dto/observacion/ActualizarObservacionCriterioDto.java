package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.observacion;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class ActualizarObservacionCriterioDto extends RegistrarObservacionCriterioDto {

    @NotNull(message = "El identificador de la observación es obligatorio")
    private UUID id;

    public UUID id() {
        return id;
    }

}

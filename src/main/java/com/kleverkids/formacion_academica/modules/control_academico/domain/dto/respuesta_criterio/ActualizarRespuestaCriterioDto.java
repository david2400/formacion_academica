package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.respuesta_criterio;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;



@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class ActualizarRespuestaCriterioDto extends RegistrarRespuestaCriterioDto {

    @NotNull(message = "El identificador de la respuesta es obligatorio")
    private Long id;

    public Long id() {
        return id;
    }

}

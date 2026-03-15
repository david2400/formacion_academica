package com.kleverkids.formacion_academica.modules.control_academico.domain.dto.examen;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Objects;


@Getter
@Setter
@NoArgsConstructor
public class MoverExamenDto {

    @NotNull(message = "El examen es obligatorio")
    private Long examenId;

    @NotNull(message = "La clase destino es obligatoria")
    private Long claseDestinoId;

    public MoverExamenDto(Long examenId, Long claseDestinoId) {
        this.examenId = Objects.requireNonNull(examenId, "El examen es obligatorio");
        this.claseDestinoId = Objects.requireNonNull(claseDestinoId, "La clase destino es obligatoria");
    }
}
